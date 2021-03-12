package com.smart.home.controller.app;

import com.google.common.base.Joiner;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.controller.app.request.CommunityUserBanDTO;
import com.smart.home.controller.app.request.CommunityUserForbiddenSpeechDTO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.user.entity.UserCommunityAuth;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.modules.user.service.UserCommunityAuthService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Api(tags = "社区-封禁/禁言用户")
@RestController
@RequestMapping("/api/app/communityUser")
public class AppCommunityUserController {

    @Autowired
    private UserCommunityAuthService userCommunityAuthService;
    @Autowired
    private UserAccountService userAccountService;

    @ApiOperation("封禁用户")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/banUser")
    public APIResponse banUser(@Valid @RequestBody CommunityUserBanDTO communityUserBanDTO, BindingResult bindingResult) {
        Long createdBy = UserUtils.getLoginUserId();
        Long userId = communityUserBanDTO.getUserId();
        UserCommunityAuth userCommunityAuth = new UserCommunityAuth();
        userCommunityAuth.setCreatedBy(createdBy);
        userCommunityAuth.setCreatedTime(new Date());
        userCommunityAuth.setBlackFlag(YesNoEnum.YES.getCode());
        userCommunityAuth.setEffectiveStartDate(communityUserBanDTO.getEffectiveStartDate());
        userCommunityAuth.setEffectiveEndDate(communityUserBanDTO.getEffectiveEndDate());
        userCommunityAuth.setUserId(communityUserBanDTO.getUserId());
        userCommunityAuth.setRemark(Joiner.on(",").join(communityUserBanDTO.getReasonList()));
        userCommunityAuthService.createOrUpdate(userCommunityAuth);
        // 封禁后踢出登陆状态
        userAccountService.doLogout(userId);
        return APIResponse.OK();
    }

    @ApiOperation("禁言用户")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/forbiddenSpeech")
    public APIResponse forbiddenSpeech(@Valid @RequestBody CommunityUserForbiddenSpeechDTO communityUserForbiddenSpeechDTO, BindingResult bindingResult) {
        Long createdBy = UserUtils.getLoginUserId();
        UserCommunityAuth userCommunityAuth = new UserCommunityAuth();
        userCommunityAuth.setCreatedBy(createdBy);
        userCommunityAuth.setCreatedTime(new Date());
        userCommunityAuth.setSpeakFlag(YesNoEnum.YES.getCode());
        userCommunityAuth.setUserId(communityUserForbiddenSpeechDTO.getUserId());
        userCommunityAuth.setRemark(Joiner.on(",").join(communityUserForbiddenSpeechDTO.getReasonList()));
        userCommunityAuthService.createOrUpdate(userCommunityAuth);
        return APIResponse.OK();
    }

}
