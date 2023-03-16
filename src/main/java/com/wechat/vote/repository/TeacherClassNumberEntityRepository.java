package com.wechat.vote.repository;

import com.wechat.vote.entity.TeacherClassNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherClassNumberEntityRepository extends JpaRepository<TeacherClassNumberEntity, Long> {
    List<TeacherClassNumberEntity> findAllByTeacherNumber(String teacherNumber);
}
