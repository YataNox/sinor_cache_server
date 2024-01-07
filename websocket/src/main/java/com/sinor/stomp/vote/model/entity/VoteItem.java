package com.sinor.stomp.vote.model.entity;

import com.sinor.stomp.vote.common.BaseEntity;
import com.sinor.stomp.vote.model.dto.response.VoteItemResponseDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class VoteItem implements BaseEntity<Long> {
    // parent
    private Long voteId;

    // vote > vote_candidate > vote_log
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // child
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "voteItemId")
    private Set<VoteLog> voteLogs;

    // properties
    private String content;

    @Version
    private Long version;

    @Builder
    public VoteItem(Long voteId, String content) {
        this.voteId = voteId;
        this.content = content;
    }

    public VoteItemResponseDto fromEntitytoResponseDto() {
        return VoteItemResponseDto.builder()
                .id(id)
                .content(content)
                .voteLogs(voteLogs != null
                        ? voteLogs.stream().map(VoteLog::fromEntitytoResponseDto).toList()
                        : null)
                .count(voteLogs != null
                        ? voteLogs.size()
                        : null)
                .build();
    }
}
