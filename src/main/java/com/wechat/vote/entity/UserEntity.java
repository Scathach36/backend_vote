package com.wechat.vote.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    private String username;
    private String password;
    private String name;
    private String number;
    @Column(name = "class", nullable = true)
    private String classNumber;
    private String role;

    private String openid;
}