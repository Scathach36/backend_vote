package com.wechat.vote.controller;

import com.wechat.vote.entity.VoteEntity;
import com.wechat.vote.entity.VoteOptionEntity;
import com.wechat.vote.repository.VoteEntityRepository;
import com.wechat.vote.repository.VoteOptionEntityRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "投票管理")
@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteEntityRepository voteEntityRepository;

    @Autowired
    private VoteOptionEntityRepository voteOptionEntityRepository;

    @ApiOperation("获取所有投票信息")
    @GetMapping("/getVote")
    @ResponseBody
    public List<VoteEntity> getAllVote() { return voteEntityRepository.findAll(); }

    @ApiOperation("获取所有投票选项")
    @GetMapping("/getVoteOption")
    @ResponseBody
    public List<VoteOptionEntity> getAllVoteOption() { return voteOptionEntityRepository.findAll(); }

    @ApiOperation("新增投票")
    @PostMapping("/saveOne")
    @ResponseBody
    public Map<String, Object> saveOne(@RequestBody VoteEntity voteEntity) {
        Map<String, Object> json = new HashMap<>();
        VoteEntity vote = new VoteEntity();

        Date createTime = new Date();
        Date endTime = voteEntity.getEmdTime();

        vote.setCreateTime(createTime);
        vote.setEmdTime(endTime);

        voteEntityRepository.save(vote);
        json.put("code",200);
        json.put("msg","新增投票成功");
        json.put("time",createTime);

        return json;
    }
}
