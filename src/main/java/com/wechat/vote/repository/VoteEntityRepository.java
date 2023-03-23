package com.wechat.vote.repository;

import com.wechat.vote.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface VoteEntityRepository extends JpaRepository<VoteEntity, Long> {
    List<VoteEntity> findAllByClassNumber(String classNumber);

    VoteEntity findById(int id);

    List<VoteEntity> findByTitleLike(String title);

    List<VoteEntity> findAllByCreateBy(String createBy);

    @Transactional
    void deleteById(int id);

    @Modifying
    @Transactional
    @Query("delete from VoteEntity v where v.id in ?1")
    void deleteSomeVotes(List<Integer> ids);
}
