package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.community.entity.CommunityStampHistory;
import com.smart.home.modules.community.service.CommunityStampHistoryService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "community帖子点踩历史接口")
@RestController
@RequestMapping("/api/pc/communityStampHistory")
public class CommunityStampHistoryController {

    @Autowired
    private CommunityStampHistoryService communityStampHistoryService;

    @ApiOperation("创建帖子点踩历史")
    @PostMapping("/create")
    public APIResponse create(CommunityStampHistory communityStampHistory) {
        return APIResponse.OK(communityStampHistoryService.create(communityStampHistory));
    }

    @ApiOperation("更新帖子点踩历史")
    @PostMapping("/update")
    public APIResponse update(CommunityStampHistory communityStampHistory) {
        return APIResponse.OK(communityStampHistoryService.update(communityStampHistory));
    }

    @ApiOperation("删除帖子点踩历史")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        communityStampHistoryService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询帖子点踩历史")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<CommunityStampHistory>> selectByPage(CommunityStampHistory communityStampHistory, int pageNum, int pageSize) {
        List<CommunityStampHistory> list = communityStampHistoryService.selectByPage(communityStampHistory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询帖子点踩历史")
    @GetMapping("/selectById")
    public APIResponse<CommunityStampHistory> selectById(Long id) {
        return APIResponse.OK(communityStampHistoryService.findById(id));
    }

}