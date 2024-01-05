package com.sinor.stomp.vote.model.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record VoteRequestDto(
        Long boardId,

        LocalDateTime validUntil,
        Boolean isAnonymous,
        Boolean isMultiple,
        List<VoteItemRequestDto> voteItems
) {
}
