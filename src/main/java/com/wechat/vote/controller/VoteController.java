package com.wechat.vote.controller;

import com.wechat.vote.entity.VoteEntity;
import com.wechat.vote.entity.VoteOptionEntity;
import com.wechat.vote.repository.VoteEntityRepository;
import com.wechat.vote.repository.VoteOptionEntityRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @PostMapping("/saveVote")
    public Map<String, Object> saveVote(@RequestBody VoteEntity voteEntity) {
        Map<String, Object> json = new HashMap<>();
        VoteEntity vote = new VoteEntity();

        Date createTime = new Date();

        vote.setTitle(voteEntity.getTitle());
        vote.setDescription(voteEntity.getDescription());
        vote.setMulti(voteEntity.getMulti());
        vote.setAnonymous(voteEntity.getAnonymous());
        vote.setCreateBy(voteEntity.getCreateBy());
        vote.setCreateTime(createTime);
        vote.setEndTime(voteEntity.getEndTime());
        vote.setClassNumber(voteEntity.getClassNumber());

        VoteEntity res = voteEntityRepository.save(vote);
        int voteId = res.getId();

        json.put("code",200);
        json.put("msg","新增投票成功");
        json.put("title",voteEntity.getTitle());
        json.put("time",createTime);
        json.put("endTime", voteEntity.getEndTime());
        json.put("id",voteId);

        return json;
    }

    @ApiOperation("投票选项录入")
    @PostMapping("/saveAllOptions")
    @ResponseBody
    public Map<String, Object> saveAllOptions(@RequestBody List<VoteOptionEntity> voteOptionEntities) {
        Map<String, Object> json = new HashMap<>();

        Date createTime = new Date();

        for(VoteOptionEntity option : voteOptionEntities) {
            option.setCreateTime(createTime);
        }

        voteOptionEntityRepository.saveAll(voteOptionEntities);

        json.put("code",200);
        json.put("msg","选项添加成功");

        return json;
    }

    @ApiOperation("根据班级查找投票")
    @PostMapping("/findAllByClassNumber")
    @ResponseBody
    public Map<String, Object> findAllByClassNumber(@RequestBody VoteEntity voteEntity) {
        Map<String, Object> json = new HashMap<>();
        List<VoteEntity> voteList = voteEntityRepository.findAllByClassNumber(voteEntity.getClassNumber());

        json.put("code",200);
        json.put("list", voteList);

        return json;
    }
}
