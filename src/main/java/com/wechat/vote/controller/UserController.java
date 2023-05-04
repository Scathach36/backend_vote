package com.wechat.vote.controller;

import com.wechat.vote.entity.UserEntity;
import com.wechat.vote.repository.UserEntityRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

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
    public UserEntity findUser(@ApiParam("用户名")@RequestBody String username) {
        return userEntityRepository.findByUsername(username);
    }

    @ApiOperation("添加新用户/修改用户信息")
    @PostMapping("/save")
    public int save(@ApiParam("用户")@RequestBody UserEntity user) {
       userEntityRepository.save(user);

       return userEntityRepository.findByOpenid(user.getOpenid()).getId();
    }

    @ApiOperation("查询用户名是否存在")
    @PostMapping("/existsByUsername")
    public boolean existsByUsername(@ApiParam("用户名")@RequestBody UserEntity user) {
        return userEntityRepository.existsByUsername(user.getUsername());
    }

    @ApiOperation("查询学号是否存在")
    @PostMapping("/existsByNumber")
    public boolean existsByNumber(@ApiParam("用户名")@RequestBody UserEntity user) {
        return userEntityRepository.existsByNumber(user.getNumber());
    }

    @ApiOperation("用户信息绑定")
    @PostMapping("/bindUser")
    public Map<String, Object> bindUser(@ApiParam("用户信息")@RequestBody UserEntity user) {
        Map<String, Object> json = new HashMap<>();

        if (userEntityRepository.existsByNumber(user.getNumber())) {
            json.put("code",203);
            json.put("msg","该学号已被注册！");
            return json;
        }

        UserEntity userInfo = userEntityRepository.findById(user.getId());

        if (userInfo != null) {
            if (userInfo.getOpenid() == null) {
                userInfo.setName(user.getName());
                userInfo.setNumber(user.getNumber());
                userInfo.setClassNumber(user.getClassNumber());

                userEntityRepository.save(userInfo);
                json.put("code",200);
                json.put("msg","信息绑定成功");
            } else {
                json.put("code",202);
                json.put("msg","该账号已被绑定！");
            }
            return json;
        } else {
            json.put("code",201);
            json.put("msg","Error");
        }

        return json;

    }

}
