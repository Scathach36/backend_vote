package com.wechat.vote.controller;

import com.wechat.vote.entity.TeacherClassNumberEntity;
import com.wechat.vote.entity.UserEntity;
import com.wechat.vote.repository.TeacherClassNumberEntityRepository;
import com.wechat.vote.repository.UserEntityRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "班级相关接口")
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private TeacherClassNumberEntityRepository teacherClassNumberEntityRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @ApiOperation("获取教师对应班级")
    @PostMapping("/findClass")
    public Map<String, Object> findClassByTeacherNumber(@ApiParam("用户名")@RequestBody UserEntity teacher) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        List<TeacherClassNumberEntity> teacherClassNumberEntityList = teacherClassNumberEntityRepository.findAllByTeacherNumber(teacher.getNumber());
        List<String> classList = new ArrayList<>();

        for(TeacherClassNumberEntity item:teacherClassNumberEntityList) {
            classList.add(item.getClassNumber());
        }

        data.put("classNumber",classList);
        json.put("code", "200");
        json.put("data",data);

        return json;
    }
}
