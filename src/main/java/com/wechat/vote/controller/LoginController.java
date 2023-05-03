package com.wechat.vote.controller;

import com.wechat.vote.entity.UserEntity;
import com.wechat.vote.repository.UserEntityRepository;
import com.wechat.vote.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "登录相关接口")
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserEntityRepository userEntityRepository;

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
            data.put("name",userEntity.getName());
            data.put("id",userEntity.getId());
            json.put("data",data);
            return json;
        } else {
            json.put("code","200");
            json.put("msg","密码错误");
            return json;
        }
    }

    @ApiOperation("微信授权登录")
    @PostMapping("/wxLogin")
    public Map<String, Object> wxLogin(@ApiParam("微信请求体")@RequestBody Map<String, Object> data) {
        String openid = (String) data.get("openid");

        UserEntity userEntity = userEntityRepository.findByOpenid(openid);
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        if (userEntity == null) {
            json.put("code",201);
            json.put("msg","用户不存在");
            return json;
        }else {
            json.put("code",200);
            json.put("msg","登录成功");
            response.put("token",JwtUtil.createToken(userEntity.getUsername(), userEntity.getRole()));
            response.put("role",userEntity.getRole());
            response.put("classNumber",userEntity.getClassNumber());
            response.put("number",userEntity.getNumber());
            response.put("name",userEntity.getName());
            response.put("id",userEntity.getId());
            json.put("data",response);
            return json;
        }
    }

    @ApiOperation("微信绑定已有账号")
    @PostMapping("/bindUser")
    public Map<String, Object> bindUser(@ApiParam("账号密码和openid")@RequestBody UserEntity user) {
        String openid = user.getOpenid();
        String username = user.getUsername();
        String password = user.getPassword();

        UserEntity userEntity = userEntityRepository.findByUsernameAndPassword(username, password);
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        if (userEntity == null) {
            json.put("code",201);
            json.put("msg","用户名或密码错误");
            return json;
        }else {
            userEntity.setOpenid(openid);
            userEntityRepository.save(userEntity);
            json.put("code",200);
            json.put("msg","登录成功");
            data.put("token",JwtUtil.createToken(userEntity.getUsername(), userEntity.getRole()));
        }
        return json;
    }

    @ApiOperation("创建token")
    @PostMapping("/createToken")
    public Map<String, Object> createToken(@ApiParam("openid")@RequestBody String openid) {
        Map<String, Object> json = new HashMap<>();

        json.put("code",200);
        json.put("token",JwtUtil.createToken(openid));

        return json;
    }
}
