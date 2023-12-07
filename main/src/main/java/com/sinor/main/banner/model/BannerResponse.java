package com.sinor.main.banner.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BannerResponse {
	private boolean ok;
	private List<Banner> data;

}
