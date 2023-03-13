package com.wechat.vote.controller;

import com.wechat.vote.entity.LoginEntity;
import com.wechat.vote.entity.UserEntity;
import com.wechat.vote.repository.UserEntityRepository;
import com.wechat.vote.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/checkToken")
    public Boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        return JwtUtil.checkToken(token);
    }

    @ApiOperation("用户登录")
    @GetMapping("/login")
    public LoginEntity login(@ApiParam("用户名")@RequestParam("username")String username, @ApiParam("密码")@RequestParam("password") String password) {
        UserEntity user = userEntityRepository.findByUsername(username);
        if (user == null) {
            LoginEntity loginInfo = new LoginEntity();
            loginInfo.setCode("200");
            loginInfo.setMsg("用户不存在");
            return loginInfo;
        }
        if (user.getPassword().equals(password)) {
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
