package com.sinor.main.gatheringcontent;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinor.main.gatheringcontent.model.GatheringContent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatheringContentService {
	private final GatheringContentRepository gatheringContentRepository;

	public List<GatheringContent> getPopular() {
		return gatheringContentRepository.findAll();
	}
}
