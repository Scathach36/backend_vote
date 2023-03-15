package com.wechat.vote.controller;

import com.wechat.vote.entity.VoteEntity;
import com.wechat.vote.repository.VoteEntityRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "投票管理")
@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteEntityRepository voteEntityRepository;

    @ApiOperation("获取所有投票信息")
    @GetMapping("/get")
    @ResponseBody
    public List<VoteEntity> getAll() { return voteEntityRepository.findAll(); }
}
