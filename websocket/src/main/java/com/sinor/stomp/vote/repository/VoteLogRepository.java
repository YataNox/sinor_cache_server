package com.sinor.stomp.vote.repository;

import com.sinor.stomp.vote.model.entity.VoteLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteLogRepository extends JpaRepository<VoteLog, Long> {
}
