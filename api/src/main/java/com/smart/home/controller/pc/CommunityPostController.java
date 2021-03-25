package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.controller.pc.request.community.CommunityPostCreateDTO;
import com.smart.home.controller.pc.request.community.CommunityPostUpdateDTO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "社区-帖子相关接口")
@RestController
@RequestMapping("/api/pc/communityPost")
public class CommunityPostController {

    @Autowired
    private CommunityPostService communityPostService;

    @ApiOperation("发帖")
    @RoleAccess({RoleConsts.CREATOR,RoleConsts.REGISTER})
    @PostMapping("/create")
    public APIResponse create(@Valid CommunityPostCreateDTO communityPostCreateDTO, BindingResult bindingResult) {
        CommunityPost communityPost = new CommunityPost();
        BeanUtils.copyProperties(communityPostCreateDTO, communityPost);
        communityPost.setUserId(UserUtils.getLoginUserId());
        communityPost.setCommunity(communityPostCreateDTO.getCommunityId());
        return APIResponse.OK(communityPostService.create(communityPost));
    }

    @ApiOperation("修改发帖")
    @RoleAccess({RoleConsts.CREATOR,RoleConsts.REGISTER})
    @PostMapping("/update")
    public APIResponse update(@Valid CommunityPostUpdateDTO communityPostUpdateDTO, BindingResult bindingResult) {
        CommunityPost communityPost = new CommunityPost();
        BeanUtils.copyProperties(communityPostUpdateDTO, communityPost);
        communityPost.setUpdatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(communityPostService.update(communityPost));
    }

    @ApiOperation("删除社区帖子(包括草稿)")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        communityPostService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("按主键ID查询社区帖子")
    @GetMapping("/selectById")
    public APIResponse<CommunityPost> selectById(Long id) {
        return APIResponse.OK(communityPostService.findById(id));
    }

}