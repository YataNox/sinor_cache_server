package com.example.demo.src.controller;


import com.example.demo.common.BaseException;
import com.example.demo.common.BaseResponse;
import com.example.demo.src.dto.GetUserRes;
import com.example.demo.src.dto.PatchUserReq;
import com.example.demo.src.dto.PostUserReq;
import com.example.demo.src.dto.PostUserRes;
import com.example.demo.src.entity.User;
import com.example.demo.src.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.common.BaseResponseStatus.POST_USERS_EMPTY_EMAIL;
import static com.example.demo.common.BaseResponseStatus.POST_USERS_INVALID_EMAIL;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app/users")
public class UserController {


    private final UserService userService;

    /**
     * 회원가입 API
     * [POST] /app/users
     *
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if (postUserReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if (!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 조회 API
     * [GET] /app/users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /app/users? Email=
     *
     * @return BaseResponse<List < GetUserRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:{port}/app/users
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) {
        try {
            if (Email == null) {
                List<GetUserRes> getUsersRes = userService.getUsers();
                return new BaseResponse<>(getUsersRes);
            }
            // Get Users
            List<GetUserRes> getUsersRes = userService.getUsersByEmail(Email);
            return new BaseResponse<>(getUsersRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 1명 조회 API
     * [GET] /app/users/:userId
     *
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userId}") // (GET) 127.0.0.1:9000/app/users/:userId
    public BaseResponse<GetUserRes> getUser(@PathVariable("userId") int userId) {
        // Get Users
        try {
            GetUserRes getUserRes = userService.getUser(userId);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /**
     * 유저정보변경 API
     * [PATCH] /app/users/:userId
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userId}")
    public BaseResponse<String> modifyUserName(@PathVariable("userId") int userId, @RequestBody User user) {
        try {
            PatchUserReq patchUserReq = new PatchUserReq(userId, user.getName());
            userService.modifyUserName(patchUserReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저정보삭제 API
     * [DELETE] /app/users/:userId
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @DeleteMapping("/{userId}")
    public BaseResponse<String> deleteUser(@PathVariable("userId") int userId) {
        try {
            userService.deleteUser(userId);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
