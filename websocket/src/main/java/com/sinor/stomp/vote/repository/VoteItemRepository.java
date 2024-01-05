package com.sinor.stomp.vote.repository;

import com.sinor.stomp.vote.model.entity.VoteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteItemRepository extends JpaRepository<VoteItem, Long> {
}
