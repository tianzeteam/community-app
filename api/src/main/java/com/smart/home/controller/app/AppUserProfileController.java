package com.smart.home.controller.app;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.controller.app.response.MyProfileVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.entity.UserData;
import com.smart.home.modules.user.entity.UserTag;
import com.smart.home.modules.user.service.*;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/24
 **/
@Api(tags = "我")
@RestController
@RequestMapping("/api/app/userProfile")
public class AppUserProfileController {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private UserTagService userTagService;
    @Autowired
    private UserPrivacySettingService userPrivacySettingService;
    @Autowired
    private UserFocusService userFocusService;

    @ApiOperation("基本用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "根据用户主键ID查询，如果查当前登陆用户的不要传", required = false)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyRootProfile")
    public APIResponse<MyProfileVO> queryMyRootProfile(@RequestParam(value = "userId", required = false) Long userId) {
        if (Objects.isNull(userId)) {
            userId = UserUtils.getLoginUserId();
        }
        UserAccount userAccount = userAccountService.findUserByUserId(userId);
        UserData userData = userDataService.findByUserId(userId);
        UserTag userTag = userTagService.findByUserId(userId);
        MyProfileVO myProfileVO = new MyProfileVO();
        BeanUtils.copyProperties(userData, myProfileVO);
        myProfileVO.setHeadUrl(userAccount.getHeadUrl());
        myProfileVO.setNickName(userAccount.getNickName());
        if(!Objects.isNull(userTag)) {
            myProfileVO.setTag(userTag.getTag());
        }
        myProfileVO.setUserPrivatePrivacyList(userPrivacySettingService.queryUserSettingList(userId));
        return APIResponse.OK(myProfileVO);
    }

    @ApiOperation("发帖数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "根据用户主键ID查询，如果查当前登陆用户的不要传", required = false)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyRootProfilePost")
    public APIResponse queryMyRootProfileData(@RequestParam(value = "userId", required = false) Long userId) {
        if (Objects.isNull(userId)) {
            userId = UserUtils.getLoginUserId();
        }
        // TODO
        return null;
    }
    @ApiOperation("评论数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "根据用户主键ID查询，如果查当前登陆用户的不要传", required = false)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyRootProfileComment")
    public APIResponse queryMyRootProfileComment(@RequestParam(value = "userId", required = false) Long userId) {
        if (Objects.isNull(userId)) {
            userId = UserUtils.getLoginUserId();
        }
        // TODO
        return null;
    }
    @ApiOperation("回帖数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "根据用户主键ID查询，如果查当前登陆用户的不要传", required = false)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyRootProfileReply")
    public APIResponse queryMyRootProfileReply(@RequestParam(value = "userId", required = false) Long userId) {
        if (Objects.isNull(userId)) {
            userId = UserUtils.getLoginUserId();
        }
        // TODO
        return null;
    }
    @ApiOperation("评价数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "根据用户主键ID查询，如果查当前登陆用户的不要传", required = false)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyRootProfileEvaluate")
    public APIResponse queryMyRootProfileEvaluate(@RequestParam(value = "userId", required = false) Long userId) {
        if (Objects.isNull(userId)) {
            userId = UserUtils.getLoginUserId();
        }
        // TODO
        return null;
    }
    @ApiOperation("投稿数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "根据用户主键ID查询，如果查当前登陆用户的不要传", required = false)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyRootProfileContribute")
    public APIResponse queryMyRootProfileContribute(@RequestParam(value = "userId", required = false) Long userId) {
        if (Objects.isNull(userId)) {
            userId = UserUtils.getLoginUserId();
        }
        // TODO
        return null;
    }

    @ApiOperation("关注-用户点击关注按钮")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "focusUserId", value = "被关注的用户主键ID", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/focusUser")
    public APIResponse focusUser(Long focusUserId) {
        userFocusService.focusUser(focusUserId, UserUtils.getLoginUserId());
        return APIResponse.OK();
    }

}
