package com.wechat.vote.repository;

import com.wechat.vote.entity.VoteOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface VoteOptionEntityRepository extends JpaRepository<VoteOptionEntity, Long> {
    List<VoteOptionEntity> findAllByVoteId(int voteId);

    VoteOptionEntity findById(int id);

    @Modifying
    @Transactional
    @Query("delete from VoteOptionEntity v where v.id in ?1")
    void deleteSomeOptions(List<Integer> ids);

    @Modifying
    @Transactional
    @Query("delete from  VoteOptionEntity v where v.voteId in ?1")
    void deleteSomeOptionsByVoteIds(List<Integer> voteIds);
}
