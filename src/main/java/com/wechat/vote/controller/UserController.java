package com.wechat.vote.controller;

import com.wechat.vote.entity.UserEntity;
import com.wechat.vote.repository.UserEntityRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserEntityRepository userEntityRepository;

    @ApiOperation("获取所有用户信息")
    @GetMapping("/get")
    public List<UserEntity> getAll() {
        return userEntityRepository.findAll();
    }

    @ApiOperation("按用户名查找用户")
    @PostMapping("/findUser")
    public UserEntity findUser(@ApiParam("用户名")@RequestParam("username")String username) {
        return userEntityRepository.findByUsername(username);
    }

    @ApiOperation("添加新用户")
    @PostMapping("/save")
    public void save(@ApiParam("用户")UserEntity user) {
        userEntityRepository.save(user);
    }

}
