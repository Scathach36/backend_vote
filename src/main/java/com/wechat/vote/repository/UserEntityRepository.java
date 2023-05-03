package com.wechat.vote.repository;

import com.wechat.vote.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(@Param("username") String username);

    boolean existsByUsername(String username);

    boolean existsByNumber(String number);

    UserEntity findByOpenid(@Param("openid") String openid);

    UserEntity findByUsernameAndPassword(@Param("username")String username, @Param("password")String password);
}