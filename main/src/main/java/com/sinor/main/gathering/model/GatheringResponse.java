package com.sinor.main.gathering.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GatheringResponse {
	private boolean ok;
	private List<Gathering> data;

}
