package com.sinor.stomp.vote.model.dto.request;

import lombok.Builder;

@Builder
public record VoteItemRequestDto(
        Long voteId,
        String content
) {
}
