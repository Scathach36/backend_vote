package com.wechat.vote.repository;

import com.wechat.vote.entity.VoteTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteTicketEntityRepository extends JpaRepository<VoteTicketEntity, Long> {
}
