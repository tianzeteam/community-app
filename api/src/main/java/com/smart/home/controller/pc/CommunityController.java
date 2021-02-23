package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.community.entity.Community;
import com.smart.home.modules.community.service.CommunityService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "community社区接口")
@RestController
@RequestMapping("/api/pc/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @ApiOperation("创建社区")
    @PostMapping("/create")
    public APIResponse create(Community community) {
        community.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(communityService.create(community));
    }

    @ApiOperation("更新社区")
    @PostMapping("/update")
    public APIResponse update(Community community) {
        community.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(communityService.update(community));
    }

    @ApiOperation("删除社区")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        communityService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询社区")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<Community>> selectByPage(Community community, int pageNum, int pageSize) {
        List<Community> list = communityService.selectByPage(community, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询社区")
    @GetMapping("/selectById")
    public APIResponse<Community> selectById(Long id) {
        return APIResponse.OK(communityService.findById(id));
    }

}