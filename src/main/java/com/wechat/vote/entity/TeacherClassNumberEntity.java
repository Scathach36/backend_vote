package com.wechat.vote.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "teacher_classnumber")
public class TeacherClassNumberEntity {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    private String teacherNumber;
    private String classNumber;
}
