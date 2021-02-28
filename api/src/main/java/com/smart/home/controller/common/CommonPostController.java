package com.smart.home.controller.common;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.community.service.CommunityPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Api(tags = "社区帖子-精华/置顶")
@RestController
@RequestMapping("/api/common/post")
public class CommonPostController {

    @Autowired
    private CommunityPostService communityPostService;

    @ApiOperation("设置为精华")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "帖子主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/setBoutique")
    public APIResponse setBoutique(Long postId) {
        communityPostService.updateBoutiqueFlag(postId, YesNoEnum.YES.getCode());
        return APIResponse.OK();
    }

    @ApiOperation("取消精华")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "帖子主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/cancelBoutique")
    public APIResponse cancelBoutique(Long postId) {
        communityPostService.updateBoutiqueFlag(postId, YesNoEnum.NO.getCode());
        return APIResponse.OK();
    }

    @ApiOperation("设置为置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "帖子主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/setTop")
    public APIResponse setTop(Long postId) {
        communityPostService.updateTopFlag(postId, YesNoEnum.YES.getCode());
        return APIResponse.OK();
    }

    @ApiOperation("取消置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "帖子主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/cancelTop")
    public APIResponse cancelTop(Long postId) {
        communityPostService.updateTopFlag(postId, YesNoEnum.NO.getCode());
        return APIResponse.OK();
    }

}
