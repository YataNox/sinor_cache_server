package com.sinor.stomp.vote.model.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record VoteItemResponseDto(
        Long id,
        String content,
        List<VoteLogResponseDto> voteLogs,
        Integer count
) {
}
