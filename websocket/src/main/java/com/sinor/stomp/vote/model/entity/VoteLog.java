package com.sinor.stomp.vote.model.entity;

import com.sinor.stomp.vote.common.BaseEntity;
import com.sinor.stomp.vote.model.dto.response.VoteLogResponseDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class VoteLog implements BaseEntity<Long> {
    // parent
    private Long voteItemId;

    // vote_candidate > vote_log <-> member
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // mapping
    private Long memberId;

    // properties

    @Version
    private Long version;

    @Builder
    public VoteLog(Long voteItemId, Long memberId) {
        this.voteItemId = voteItemId;
        this.memberId = memberId;
    }

    public VoteLogResponseDto fromEntitytoResponseDto() {
        return VoteLogResponseDto.builder()
                .id(id)
                .memberId(memberId)
                .voteItemId(voteItemId)
                .build();
    }
}
