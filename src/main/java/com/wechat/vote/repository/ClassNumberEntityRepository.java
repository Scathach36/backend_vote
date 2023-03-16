package com.wechat.vote.repository;

import com.wechat.vote.entity.ClassNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassNumberEntityRepository extends JpaRepository<ClassNumberEntity, Long> {
}
