package com.sinor.main.banner;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinor.main.banner.model.Banner;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BannerService {
	private final BannerRepository bannerRepository;

	public List<Banner> getBanner() {
		return bannerRepository.findAll();
	}
}
