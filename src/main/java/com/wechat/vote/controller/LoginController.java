package com.wechat.vote.controller;

import com.wechat.vote.entity.LoginEntity;
import com.wechat.vote.entity.UserEntity;
import com.wechat.vote.repository.UserEntityRepository;
import com.wechat.vote.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public LoginEntity login(@ApiParam("用户")UserEntity user) {
        UserEntity userEntity = userEntityRepository.findByUsername(user.getUsername());
        if (userEntity == null) {
            LoginEntity loginInfo = new LoginEntity();
            loginInfo.setCode("200");
            loginInfo.setMsg("用户不存在");
            return loginInfo;
        }
        if (userEntity.getPassword().equals(user.getPassword())) {
            LoginEntity loginInfo = new LoginEntity();
            loginInfo.setCode("200");
            loginInfo.setMsg("登录成功");
            loginInfo.setToken(JwtUtil.createToken(user.getUsername(), user.getRole()));
            loginInfo.setRole(user.getRole());
            return loginInfo;
        } else {
            LoginEntity loginInfo = new LoginEntity();
            loginInfo.setCode("200");
            loginInfo.setMsg("密码错误");
            return loginInfo;
        }
    }
}
