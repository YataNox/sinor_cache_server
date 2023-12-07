package com.sinor.main.gatheringcontent;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.main.common.BaseResponse;
import com.sinor.main.gatheringcontent.model.GatheringContent;
import com.sinor.main.gatheringcontent.model.GatheringContentResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/gatheringContents/popular")
public class GatheringContentController {

	private final GatheringContentService gatheringContentService;

	@GetMapping
	public BaseResponse<GatheringContentResponse> getPopular() {
		log.info("getPopular 실행");
		List<GatheringContent> gatheringContentList = gatheringContentService.getPopular();
		return new BaseResponse<>(new GatheringContentResponse(true, gatheringContentList));
	}
}
