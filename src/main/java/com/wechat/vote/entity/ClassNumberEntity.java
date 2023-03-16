package com.wechat.vote.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "classnumber")
public class ClassNumberEntity{
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    private String classNumber;
}
