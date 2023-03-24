package com.wechat.vote.repository;

import com.wechat.vote.entity.VoteTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteTicketEntityRepository extends JpaRepository<VoteTicketEntity, Long> {
    boolean existsByUserIdAndVoteId(int userId, int voteId);

    List<VoteTicketEntity> findAllByUserIdAndVoteId(int userId, int voteId);

    List<VoteTicketEntity> findAllByVoteId(int id);
}
