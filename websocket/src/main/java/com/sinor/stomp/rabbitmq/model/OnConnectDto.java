package com.sinor.stomp.rabbitmq.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OnConnectDto {
    private Long boardId;
    private Long voteId;
    private Long memberId;

    @Builder
    public OnConnectDto(Long boardId, Long voteId, Long memberId) {
        this.boardId = boardId;
        this.voteId = voteId;
        this.memberId = memberId;
    }
}
