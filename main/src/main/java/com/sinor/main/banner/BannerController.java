package com.sinor.main.banner;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.main.banner.model.Banner;
import com.sinor.main.banner.model.BannerResponse;
import com.sinor.main.common.BaseResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/banner")
public class BannerController {

	private final BannerService bannerService;

	@GetMapping
	public BaseResponse<BannerResponse> getBanner() {
		log.info("getPopular 실행");
		List<Banner> bannerList = bannerService.getBanner();
		return new BaseResponse<>(new BannerResponse(true, bannerList));
	}
}
