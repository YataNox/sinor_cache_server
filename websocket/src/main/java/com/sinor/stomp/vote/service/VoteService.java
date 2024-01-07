package com.sinor.stomp.vote.service;

import com.sinor.stomp.vote.common.AbstractCrudService;
import com.sinor.stomp.vote.model.dto.request.VoteRequestDto;
import com.sinor.stomp.vote.model.dto.response.VoteResponseDto;
import com.sinor.stomp.vote.model.entity.Vote;
import com.sinor.stomp.vote.model.entity.VoteItem;
import com.sinor.stomp.vote.repository.VoteItemRepository;
import com.sinor.stomp.vote.repository.VoteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService extends AbstractCrudService<VoteResponseDto, VoteRequestDto, VoteRepository, Vote, Long> {

    private final VoteItemRepository voteItemRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, VoteItemRepository voteItemRepository) {
        super(voteRepository);
        this.voteItemRepository = voteItemRepository;
    }

    @Override
    public Vote fromRequestDtoToEntity(VoteRequestDto voteRequestDto) {
        return Vote.builder()
                .boardId(voteRequestDto.boardId())
                .isAnonymous(voteRequestDto.isAnonymous())
                .isMultiple(voteRequestDto.isMultiple())
                .validUntil(voteRequestDto.validUntil())
                .build();
    }

    @Override
    public VoteResponseDto fromEntitytoResponseDto(Vote entity) {
        return VoteResponseDto.builder()
                .id(entity.getId())
                .validUntil(entity.getValidUntil())
                .isAnonymous(entity.getIsAnonymous())
                .isMultiple(entity.getIsMultiple())
                .voteItems(entity.getItems() != null
                        ? entity.getItems().stream().map(VoteItem::fromEntitytoResponseDto).toList()
                        : null)
                .totalCount(entity.getItems() != null
                        ? getTotalCount(entity.getItems())
                        : 0)
                .build();
    }

    private Integer getTotalCount(List<VoteItem> items) {
        Integer sum = 0;
        for (VoteItem e : items) {
            sum += e.fromEntitytoResponseDto().count();
        }
        return sum;
    }

    @Override
    public VoteResponseDto updateObject(Long id, VoteRequestDto voteRequestDto) {
        Vote entity = repository.findById(id).orElseThrow();
        entity.setIsAnonymous(voteRequestDto.isAnonymous());
        entity.setIsMultiple(voteRequestDto.isMultiple());
        entity.setValidUntil(voteRequestDto.validUntil());
        return fromEntitytoResponseDto(repository.save(entity));
    }


    @Override
    public VoteResponseDto createObject(VoteRequestDto requestDto) {
        Vote voteDidSave = repository.save(fromRequestDtoToEntity(requestDto));
        requestDto.voteItems().forEach(e -> {
            VoteItem item = VoteItem.builder()
                    .voteId(voteDidSave.getId())
                    .content(e.content())
                    .build();
            voteItemRepository.save(item);
        });
        return fromEntitytoResponseDto(voteDidSave);
    }
}
