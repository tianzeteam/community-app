package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.community.entity.CommunityPostReply;
import com.smart.home.modules.community.service.CommunityPostReplyService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "community社区帖子评论接口")
@RestController
@RequestMapping("/api/pc/communityPostReply")
public class CommunityPostReplyController {

    @Autowired
    private CommunityPostReplyService communityPostReplyService;

    @ApiOperation("创建社区帖子评论")
    @PostMapping("/create")
    public APIResponse create(CommunityPostReply communityPostReply) {
        return APIResponse.OK(communityPostReplyService.create(communityPostReply));
    }

    @ApiOperation("更新社区帖子评论")
    @PostMapping("/update")
    public APIResponse update(CommunityPostReply communityPostReply) {
        return APIResponse.OK(communityPostReplyService.update(communityPostReply));
    }

    @ApiOperation("删除社区帖子评论")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        communityPostReplyService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询社区帖子评论")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<CommunityPostReply>> selectByPage(CommunityPostReply communityPostReply, int pageNum, int pageSize) {
        List<CommunityPostReply> list = communityPostReplyService.selectByPage(communityPostReply, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询社区帖子评论")
    @GetMapping("/selectById")
    public APIResponse<CommunityPostReply> selectById(Long id) {
        return APIResponse.OK(communityPostReplyService.findById(id));
    }

}