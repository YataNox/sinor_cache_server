package com.sinor.main.gathering;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinor.main.gathering.model.Gathering;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatheringService {
	private final GatheringRepository gatheringRepository;

	public List<Gathering> getGathering() {
		return gatheringRepository.findAll();
	}
}
