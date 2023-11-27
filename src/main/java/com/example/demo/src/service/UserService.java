package com.example.demo.src.service;


import com.example.demo.common.BaseException;
import com.example.demo.src.dao.UserDao;
import com.example.demo.src.dto.GetUserRes;
import com.example.demo.src.dto.PatchUserReq;
import com.example.demo.src.dto.PostUserReq;
import com.example.demo.src.dto.PostUserRes;
import com.example.demo.src.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.common.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDao userDao;
    //이메일로 비밀번호 찾기

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if (checkEmail(postUserReq.getEmail()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        try {
            int userId = userDao.createUser(postUserReq); // POINT
            return new PostUserRes(userId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try {
            int result = userDao.modifyUserName(patchUserReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteUser(int userId) throws BaseException {
        try {
            int result = userDao.deleteUser(userId);
            if (result == 0) {
                throw new BaseException(DELETE_FAIL_USERNAME);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserRes> getUsers() throws BaseException {
        try {
            List<GetUserRes> getUserRes = userDao.getUsers();
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserRes> getUsersByEmail(String email) throws BaseException {
        try {
            List<GetUserRes> getUsersRes = userDao.getUsersByEmail(email);
            return getUsersRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public GetUserRes getUser(int userId) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(userId);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkEmail(String email) throws BaseException {
        try {
            return userDao.checkEmail(email);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
