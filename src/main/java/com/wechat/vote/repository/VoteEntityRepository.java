package com.wechat.vote.repository;

import com.wechat.vote.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteEntityRepository extends JpaRepository<VoteEntity, Long> {
}
