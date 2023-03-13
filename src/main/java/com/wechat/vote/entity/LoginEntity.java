package com.wechat.vote.entity;

import javax.persistence.Entity;

public class LoginEntity {
    private String code;
    private String msg;
    private String token;
    private String role;

    public LoginEntity(String code, String msg, String token, String role) {
        this.code = code;
        this.msg = msg;
        this.token = token;
        this.role = role;
    }

    public LoginEntity() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
