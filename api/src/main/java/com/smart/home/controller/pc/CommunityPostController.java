package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "community社区帖子接口")
@RestController
@RequestMapping("/api/pc/communityPost")
public class CommunityPostController {

    @Autowired
    private CommunityPostService communityPostService;

    @ApiOperation("创建社区帖子")
    @PostMapping("/create")
    public APIResponse create(CommunityPost communityPost) {
        return APIResponse.OK(communityPostService.create(communityPost));
    }

    @ApiOperation("更新社区帖子")
    @PostMapping("/update")
    public APIResponse update(CommunityPost communityPost) {
        return APIResponse.OK(communityPostService.update(communityPost));
    }

    @ApiOperation("删除社区帖子")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        communityPostService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询社区帖子")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<CommunityPost>> selectByPage(CommunityPost communityPost, int pageNum, int pageSize) {
        List<CommunityPost> list = communityPostService.selectByPage(communityPost, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询社区帖子")
    @GetMapping("/selectById")
    public APIResponse<CommunityPost> selectById(Long id) {
        return APIResponse.OK(communityPostService.findById(id));
    }

}