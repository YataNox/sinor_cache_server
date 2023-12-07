package com.sinor.main.gathering;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.main.common.BaseResponse;
import com.sinor.main.gathering.model.Gathering;
import com.sinor.main.gathering.model.GatheringResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/gathering")
public class GatheringController {

	private final GatheringService gatheringService;

	@GetMapping
	public BaseResponse<GatheringResponse> getGathering() {
		log.info("getPopular 실행");
		List<Gathering> gatheringList = gatheringService.getGathering();
		return new BaseResponse<>(new GatheringResponse(true, gatheringList));
	}
}
