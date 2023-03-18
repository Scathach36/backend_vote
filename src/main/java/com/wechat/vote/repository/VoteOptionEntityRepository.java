package com.wechat.vote.repository;

import com.wechat.vote.entity.VoteOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteOptionEntityRepository extends JpaRepository<VoteOptionEntity, Long> {
    List<VoteOptionEntity> findAllByVoteId(int voteId);
}
