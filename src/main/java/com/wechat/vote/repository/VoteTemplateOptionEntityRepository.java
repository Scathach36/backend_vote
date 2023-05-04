package com.wechat.vote.repository;

import com.wechat.vote.entity.VoteOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteTemplateOptionEntityRepository extends JpaRepository<VoteOptionEntity, Long> {
}
