package com.sinor.stomp.vote.service;

import com.sinor.stomp.rabbitmq.service.VoteLogMessageService;
import com.sinor.stomp.vote.common.AbstractCrudService;
import com.sinor.stomp.vote.model.dto.request.VoteLogRequestDto;
import com.sinor.stomp.vote.model.dto.response.VoteLogResponseDto;
import com.sinor.stomp.vote.model.entity.VoteLog;
import com.sinor.stomp.vote.repository.VoteLogRepository;
import java.util.NoSuchElementException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class VoteLogService extends
        AbstractCrudService<VoteLogResponseDto, VoteLogRequestDto, VoteLogRepository, VoteLog, Long> {


    private final VoteLogMessageService voteLogMessageService;


    @Autowired
    public VoteLogService(VoteLogRepository voteLogRepository, VoteLogMessageService voteLogMessageService) {
        super(voteLogRepository);
        this.voteLogMessageService = voteLogMessageService;
    }


    @Override
    public VoteLog fromRequestDtoToEntity(VoteLogRequestDto requestDto) {
        return VoteLog.builder()
                .voteItemId(requestDto.voteItemId())
                .memberId(requestDto.memberId())
                .build();
    }

    @Override
    public VoteLogResponseDto fromEntitytoResponseDto(VoteLog entity) {
        return VoteLogResponseDto.builder()
                .id(entity.getId())
                .memberId(entity.getMemberId())
                .voteItemId(entity.getVoteItemId())
                .build();
    }

    @Override
    public VoteLogResponseDto createObject(VoteLogRequestDto requestDto) {
        VoteLogResponseDto responseDto = super.createObject(requestDto);
        // Messaging
        voteLogMessageService.broadcastLogByItemId(responseDto, "post");
        return responseDto;
    }

    @Override
    public VoteLogResponseDto updateObject(Long id, VoteLogRequestDto requestDto) throws NoSuchElementException {
        VoteLog entity = repository.findById(id).orElseThrow();
        VoteLogResponseDto voteLogToDelete = fromEntitytoResponseDto(entity);
        entity.setVoteItemId(requestDto.voteItemId());
        VoteLogResponseDto voteLogToPost = fromEntitytoResponseDto(repository.save(entity));

        voteLogMessageService.broadcastLogByItemId(voteLogToDelete, "delete");
        voteLogMessageService.broadcastLogByItemId(voteLogToPost, "post");
        return voteLogToPost;
    }


}