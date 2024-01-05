package com.sinor.stomp.vote.model.dto.response;


import lombok.Builder;

@Builder

public record VoteLogResponseDto(
        Long id,
        Long voteItemId,
        Long memberId
) {

}
