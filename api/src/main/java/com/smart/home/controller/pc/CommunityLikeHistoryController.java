package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.community.entity.CommunityLikeHistory;
import com.smart.home.modules.community.service.CommunityLikeHistoryService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "community帖子点赞历史接口")
@RestController
@RequestMapping("/api/pc/communityLikeHistory")
public class CommunityLikeHistoryController {

    @Autowired
    private CommunityLikeHistoryService communityLikeHistoryService;

    @ApiOperation("创建帖子点赞历史")
    @PostMapping("/create")
    public APIResponse create(CommunityLikeHistory communityLikeHistory) {
        return APIResponse.OK(communityLikeHistoryService.create(communityLikeHistory));
    }

    @ApiOperation("更新帖子点赞历史")
    @PostMapping("/update")
    public APIResponse update(CommunityLikeHistory communityLikeHistory) {
        return APIResponse.OK(communityLikeHistoryService.update(communityLikeHistory));
    }

    @ApiOperation("删除帖子点赞历史")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        communityLikeHistoryService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询帖子点赞历史")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<CommunityLikeHistory>> selectByPage(CommunityLikeHistory communityLikeHistory, int pageNum, int pageSize) {
        List<CommunityLikeHistory> list = communityLikeHistoryService.selectByPage(communityLikeHistory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询帖子点赞历史")
    @GetMapping("/selectById")
    public APIResponse<CommunityLikeHistory> selectById(Long id) {
        return APIResponse.OK(communityLikeHistoryService.findById(id));
    }

}