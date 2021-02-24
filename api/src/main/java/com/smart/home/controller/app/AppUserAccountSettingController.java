package com.smart.home.controller.app;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.controller.app.response.UserProfileVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.modules.user.service.UserDataService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jason
 * @date 2021/2/23
 **/
@Api(tags = "设置-个人资料/安全与绑定")
@RestController
@RequestMapping("/api/app/userAccountSetting")
public class AppUserAccountSettingController {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserDataService userDataService;

    @ApiOperation("个人资料查询")
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyProfile")
    public APIResponse<UserProfileVO> queryMyProfile() {
        Long userId = UserUtils.getLoginUserId();
        UserAccount userAccount = userAccountService.queryUserAccountAndData(userId);
        UserProfileVO vo = new UserProfileVO();
        vo.setHeadUrl(userAccount.getHeadUrl());
        vo.setNickName(userAccount.getNickName());
        vo.setSign(userAccount.getUserData().getSign());
        vo.setUserId(userId);
        return APIResponse.OK(vo);
    }

    @ApiOperation("更换头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "headUrl", value = "头像地址", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/updateHeadUrl")
    public APIResponse updateHeadUrl(String headUrl) {
        userAccountService.updateHeadUrl(UserUtils.getLoginUserId(), headUrl);
        return APIResponse.OK();
    }

    @ApiOperation("修改昵称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickName", value = "昵称", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/updateNickName")
    public APIResponse updateNickName(String nickName) {
        userAccountService.updateNickName(UserUtils.getLoginUserId(), nickName);
        return APIResponse.OK();
    }

    @ApiOperation("更新签名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sign", value = "签名信息", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/updateSign")
    public APIResponse updateSign(String sign) {
        userDataService.updateSign(UserUtils.getLoginUserId(), sign);
        return APIResponse.OK();
    }

    @ApiOperation("设置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "设置的密码", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/updatePassword")
    public APIResponse updatePassword(String password) {
        userAccountService.initPassword(UserUtils.getLoginUserId(), password);
        return APIResponse.OK();
    }

    @ApiOperation("绑定微信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "微信openid", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/bindWechat")
    public APIResponse bindWechat(String openid) {
        userDataService.bindWechat(UserUtils.getLoginUserId(), openid);
        return APIResponse.OK();
    }

    @ApiOperation("注销登陆")
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("logout")
    public APIResponse logout() {
        Long userId = UserUtils.getLoginUserId();
        userAccountService.doLogout(userId);
        return APIResponse.OK();
    }

}
