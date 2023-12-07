package com.sinor.main.Interest;

import java.util.List;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.main.Interest.model.Interest;
import com.sinor.main.Interest.model.InterestResponse;
import com.sinor.main.common.BaseException;
import com.sinor.main.common.BaseResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/interest")
public class InterestController {

	private final InterestService interestService;

	@GetMapping
	@ExceptionHandler(BaseException.class)
	public BaseResponse<InterestResponse> getInterest() {
		log.info("getInterest");
		// 서비스를 호출하여 데이터베이스에서 모든 관심사를 가져옵니다.
		List<Interest> interestList = interestService.getInterests();

		// InterestResponse 객체를 생성하여 반환
		return new BaseResponse<>(new InterestResponse(true, interestList));
	}

}
