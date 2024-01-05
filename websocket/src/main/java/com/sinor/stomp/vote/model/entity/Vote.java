package com.sinor.stomp.vote.model.entity;

import com.sinor.stomp.vote.common.BaseEntity;
import com.sinor.stomp.vote.model.dto.response.VoteResponseDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Vote implements BaseEntity<Long> {
    // parent
    private Long boardId;

    // board > vote > vote_candidate
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // child
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "voteId")
    private List<VoteItem> items;

    // properties
    private LocalDateTime validUntil;
    private Boolean isAnonymous;
    private Boolean isMultiple;

    @Version
    private Long version;

    @Builder
    public Vote(LocalDateTime validUntil, Boolean isAnonymous, Boolean isMultiple, Long boardId) {
        this.validUntil = validUntil;
        this.isAnonymous = isAnonymous;
        this.isMultiple = isMultiple;
        this.boardId = boardId;
    }

    public VoteResponseDto fromEntitytoResponseDto() {
        return VoteResponseDto.builder()
                .id(id)
                .validUntil(validUntil)
                .isAnonymous(isAnonymous)
                .isMultiple(isMultiple)
                .voteItems(items != null
                        ? items.stream().map(VoteItem::fromEntitytoResponseDto).toList()
                        : null)
                .totalCount(items != null
                        ? getTotalCount(items)
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
}
