package com.sinor.stomp.vote.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record VoteResponseDto(
        Long id,
        List<VoteItemResponseDto> voteItems,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime validUntil,
        Boolean isAnonymous,
        Boolean isMultiple,
        Integer totalCount
) {
}
