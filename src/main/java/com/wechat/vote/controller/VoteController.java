package com.wechat.vote.controller;

import com.wechat.vote.entity.VoteEntity;
import com.wechat.vote.entity.VoteOptionEntity;
import com.wechat.vote.entity.VoteTicketEntity;
import com.wechat.vote.repository.VoteEntityRepository;
import com.wechat.vote.repository.VoteOptionEntityRepository;
import com.wechat.vote.repository.VoteTicketEntityRepository;
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

    @Autowired
    private VoteTicketEntityRepository voteTicketEntityRepository;

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

        if (voteEntity.getId() != 0) {
            vote.setId(voteEntity.getId());
        }
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

    @ApiOperation("根据创建者查找投票")
    @PostMapping("/findAllByCreate")
    @ResponseBody
    public Map<String, Object> findAllByCreate(@RequestBody VoteEntity voteEntity) {
        Map<String, Object> json = new HashMap<>();
        List<VoteEntity> voteList = voteEntityRepository.findAllByCreateBy(voteEntity.getCreateBy());

        json.put("code",200);
        json.put("list", voteList);

        return json;
    }

    @ApiOperation("根据id查找投票")
    @PostMapping("/findById")
    public Map<String, Object> findById(@RequestBody VoteEntity voteEntity) {
        Map<String, Object> json = new HashMap<>();
        VoteEntity vote = voteEntityRepository.findById(voteEntity.getId());

        json.put("code",200);
        json.put("vote", vote);

        return json;
    }

    @ApiOperation("根据投票id查找投票选项")
    @PostMapping("/findOptionsByVoteId")
    public Map<String, Object> findOptionsByVoteId(@RequestBody VoteOptionEntity voteOptionEntity) {
        Map<String, Object> json = new HashMap<>();
        List<VoteOptionEntity> optionList = voteOptionEntityRepository.findAllByVoteId(voteOptionEntity.getVoteId());


        json.put("code",200);
        json.put("list", optionList);

        return json;
    }

    @ApiOperation("根据标题查找投票")
    @PostMapping("/findAllByTitle")
    public Map<String, Object> findAllByTitle(@RequestBody VoteEntity voteEntity) {
        Map<String, Object> json = new HashMap<>();

        List<VoteEntity> voteList = voteEntityRepository.findByTitleLike("%"+voteEntity.getTitle()+"%");

        json.put("code", 200);
        json.put("list", voteList);

        return json;
    }

    @ApiOperation("获取所有ticket选项")
    @GetMapping("/getTickets")
    public Map<String, Object> getTickets() {
        Map<String, Object> json = new HashMap<>();

        List<VoteTicketEntity> ticketList = voteTicketEntityRepository.findAll();

        json.put("code",200);
        json.put("list", ticketList);

        return json;
    }

    @ApiOperation("tickets录入")
    @PostMapping("/saveAllTickets")
    public Map<String, Object> saveAllTickets(@RequestBody List<VoteTicketEntity> ticketList) {
        Map<String, Object> json = new HashMap<>();
        List<VoteTicketEntity> saveList = new ArrayList<>();
        Date createTime = new Date();

        for(VoteTicketEntity ticket: ticketList) {
            ticket.setCreateTime(createTime);
            saveList.add(ticket);

            VoteOptionEntity option = voteOptionEntityRepository.findById(ticket.getOptionId());
            option.setNumber(option.getNumber() + 1);
        }

        json.put("code",200);
        json.put("msg","保存投票成功");
        json.put("res",voteTicketEntityRepository.saveAll(saveList));

        return json;
    }

    @ApiOperation("根据投票人id查询投票记录是否存在")
    @PostMapping("/existsByUserId")
    public Map<String, Object> existsByUserId(@RequestBody VoteTicketEntity ticket) {
        Map<String, Object> json = new HashMap<>();

        json.put("code",200);
        json.put("exist",voteTicketEntityRepository.existsByUserIdAndVoteId(ticket.getUserId(),ticket.getVoteId()));
        json.put("list",voteTicketEntityRepository.findAllByUserIdAndVoteId(ticket.getUserId(),ticket.getVoteId()));

        return json;
    }

    @ApiOperation("根据id删除投票")
    @PostMapping("/deleteVoteById")
    public Map<String, Object> deleteVoteById(@RequestBody List<VoteEntity> voteEntityList) {
        Map<String, Object> json = new HashMap<>();

        for(VoteEntity vote: voteEntityList) {
            voteEntityRepository.deleteById(vote.getId());
        }

        json.put("code",200);
        json.put("msg","投票删除成功");

        return json;
    }

    @ApiOperation("投票选项更新")
    @PostMapping("/updateOptions")
    public Map<String, Object> updateOptions(@RequestBody List<VoteOptionEntity> options) {
        Map<String, Object> json = new HashMap<>();

        List<VoteOptionEntity> oldList = voteOptionEntityRepository.findAllByVoteId(options.get(0).getVoteId());
        List<Integer> oldIds = new ArrayList<>();
        for(VoteOptionEntity item: oldList) {
            oldIds.add(item.getId());
        }

        List<Integer> iptIds = new ArrayList<>();
        for(VoteOptionEntity item: options) {
            iptIds.add(item.getId());
        }


        List<Integer> deletedIds = new ArrayList<>();
        for (Integer item: oldIds) {
            if (!iptIds.contains(item)) {
                deletedIds.add(item);
            }
        }

        List<VoteOptionEntity> saveList = new ArrayList<>();
        for (VoteOptionEntity item: options) {
            if (item.getId() == 0) {
                saveList.add(item);
            }
        }

        for (VoteOptionEntity item: options) {
            for (VoteOptionEntity oldItem: oldList) {
                if (item.getId() == oldItem.getId() && (!item.getDescription().equals(oldItem.getDescription()))) {
                    saveList.add(item);
                }
            }
        }


        if (deletedIds.size() > 0) {
            voteOptionEntityRepository.deleteSomeOptions(deletedIds);
        }
        if (saveList.size() > 0) {
            voteOptionEntityRepository.saveAll(saveList);
        }




        json.put("code",200);
        json.put("oldIds",oldIds);
        json.put("iptIds",iptIds);
        json.put("saveList",options);

        return json;
    }
}
