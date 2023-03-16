package com.wechat.vote.controller;

import com.wechat.vote.entity.UserEntity;
import com.wechat.vote.repository.UserEntityRepository;
import com.wechat.vote.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "登录相关接口")
@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserEntityRepository userEntityRepository;

    public LoginController(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @ApiOperation("token校验")
    @PostMapping("/checkToken")
    public Boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        return JwtUtil.checkToken(token);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Map<String, Object> login(@ApiParam("用户名")@RequestBody UserEntity user) {
        UserEntity userEntity = userEntityRepository.findByUsername(user.getUsername());
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        if (userEntity == null) {
            json.put("code","200");
            json.put("msg","用户不存在");
            return json;
        }
        if (userEntity.getPassword().equals(user.getPassword())) {
            json.put("code","200");
            json.put("msg","登录成功");
            data.put("token",JwtUtil.createToken(userEntity.getUsername(), userEntity.getRole()));
            data.put("role",userEntity.getRole());
            data.put("classNumber",userEntity.getClassNumber());
            data.put("number",userEntity.getNumber());
            json.put("data",data);
            return json;
        } else {
            json.put("code","200");
            json.put("msg","密码错误");
            return json;
        }
    }
}
