package com.sinor.stomp.vote.service;

import com.sinor.stomp.vote.common.AbstractCrudService;
import com.sinor.stomp.vote.model.dto.request.VoteItemRequestDto;
import com.sinor.stomp.vote.model.dto.response.VoteItemResponseDto;
import com.sinor.stomp.vote.model.entity.VoteItem;
import com.sinor.stomp.vote.model.entity.VoteLog;
import com.sinor.stomp.vote.repository.VoteItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteItemService extends
        AbstractCrudService<VoteItemResponseDto, VoteItemRequestDto, VoteItemRepository, VoteItem, Long> {


    @Autowired
    public VoteItemService(VoteItemRepository voteItemRepository) {
        super(voteItemRepository);
    }

    @Override
    protected VoteItem fromRequestDtoToEntity(VoteItemRequestDto requestDto) {
        return VoteItem.builder()
                .voteId(requestDto.voteId())
                .content(requestDto.content())
                .build();
    }

    @Override
    public VoteItemResponseDto fromEntitytoResponseDto(VoteItem entity) {
        return VoteItemResponseDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .voteLogs(entity.getVoteLogs() != null
                        ? entity.getVoteLogs().stream().map(VoteLog::fromEntitytoResponseDto).toList()
                        : null)
                .count(entity.getVoteLogs() != null
                        ? entity.getVoteLogs().size()
                        : null)
                .build();
    }

    @Override
    public VoteItemResponseDto updateObject(Long id, VoteItemRequestDto requestDto) {
        VoteItem entity = repository.findById(id).orElseThrow();
        entity.setContent(requestDto.content());
        return fromEntitytoResponseDto(repository.save(entity));
    }


}