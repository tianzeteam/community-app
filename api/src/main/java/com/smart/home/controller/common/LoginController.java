package com.smart.home.controller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smart.home.assembler.UserAssembler;
import com.smart.home.cache.SmsVerifyCodeCache;
import com.smart.home.cache.SmsVerifyCodeLimitCache;
import com.smart.home.cache.WechatAccessTokenCache;
import com.smart.home.cloud.qcloud.sms.SmsSendUtil;
import com.smart.home.cloud.wechat.login.WechatLoginUtil;
import com.smart.home.common.enums.APIResponseCodeEnum;
import com.smart.home.common.exception.RestfulRequestException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.controller.app.request.WechatLoginBindPhoneRequest;
import com.smart.home.controller.app.request.WechatLoginRequest;
import com.smart.home.dto.APIResponse;
import com.smart.home.common.util.RandomUtils;
import com.smart.home.dto.auth.User;
import com.smart.home.modules.system.entity.SysActionLog;
import com.smart.home.modules.system.service.SysActionLogService;
import com.smart.home.modules.system.service.SysConfigService;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.entity.UserData;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.user.service.UserDataService;
import com.smart.home.util.WebUtils;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/20
 **/
@Api(tags = "登陆相关接口")
@RestController
@RequestMapping("/api/public")
public class LoginController {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SysActionLogService sysActionLogService;

    @AnonAccess
    @ApiOperation(value = "用户名密码登陆")
    @PostMapping("loginByPassword")
    public APIResponse<User> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        UserAccount userAccount = null;
        try {
            userAccount = userAccountService.doAuthentication(username, password);
            // 登陆日志
            sysActionLogService.loginLog(WebUtils.getClientIpAddr(request), userAccount.getId(), "用户名密码登陆");
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        User user = UserAssembler.assemblerUser(userAccount);
        return APIResponse.OK(user);
    }

    @AnonAccess
    @ApiOperation(value = "根据token获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问令牌",required = true)
    })
    @PostMapping("queryUserInfoByToken")
    public APIResponse<User> queryUserInfoByToken(@RequestParam(required = true) String token) {
        try {
            UserAccount userAccount = userAccountService.getUserAccountByAccessToken(token);
            User user = UserAssembler.assemblerUser(userAccount);
            return APIResponse.OK(user);
        } catch (ServiceException e) {
            throw new RestfulRequestException(e.getCode(), e.getMessage());
        }
    }

    @AnonAccess
    @ApiOperation(value = "手机验证码登陆")
    @PostMapping("/loginBySmsCode")
    public APIResponse<User> loginBySmsCode(@RequestParam String mobile, @RequestParam String smsVerifyCode, HttpServletRequest request) {
        if (SmsVerifyCodeCache.isValid(mobile, smsVerifyCode)) {
            UserAccount userAccount = null;
            try {
                userAccount = userAccountService.loginViaMobile(mobile);
                sysActionLogService.loginLog(WebUtils.getClientIpAddr(request), userAccount.getId(), "手机验证码登陆");
            } catch (ServiceException e) {
                return APIResponse.ERROR(e.getMessage());
            }
            User user = UserAssembler.assemblerUser(userAccount);
            return APIResponse.OK(user);
        }
        return APIResponse.ERROR("验证码错误");
    }

    @AnonAccess
    @ApiOperation(value = "手机号和密码登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    @PostMapping("/loginByMobileAndPassword")
    public APIResponse<User> loginByMobileAndPassword(@RequestParam(required = true) String mobile,
                                             @RequestParam(required = true) String password, HttpServletRequest request) {
        UserAccount userAccount = null;
        try {
            userAccount = userAccountService.loginViaMobileAndPassword(mobile, password);
            sysActionLogService.loginLog(WebUtils.getClientIpAddr(request), userAccount.getId(), "手机号和密码登陆");
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        User user = UserAssembler.assemblerUser(userAccount);
        return APIResponse.OK(user);
    }

    /**
     * {
     * "openId": "oRrdQt4h823nm3Xf79OFEiKUjD90",
     * "nickName": "阿白",
     * "gender": 0,
     * "city": "",
     * "province": "",
     * "country": "",
     * "avatarUrl": "https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM6PoXL9wZ7rTb9niaicokPwiaAH0WibvZXSFLkSQuBPg4d5OkklibP8HAYosuqjzJIiaZtxDWtFrSnRibdGg/132",
     * "unionId": "oU5Yyt449NKWxoTJ3ohIAdcIjRoI"
     * }
     * @return
     */
    @AnonAccess
    @ApiOperation(value = "微信登陆", notes = "data返回2需要绑定手机，否则登陆登陆成功，返回用户信息")
    @PostMapping("/loginByWechat")
    public APIResponse loginByWechat(WechatLoginRequest wechatLoginRequest, HttpServletRequest request) {
//        if (!WechatAccessTokenCache.exists(wx)) {
//            return APIResponse.ERROR("非法请求");
//        }
        if (StringUtils.isBlank(wechatLoginRequest.getOpendId())) {
            return APIResponse.ERROR("openId不能为空");
        }
        UserData userData = userDataService.findByWxOpenid(wechatLoginRequest.getOpendId());
        if (Objects.isNull(userData)) {
            // 说明还没微信登陆过，需要绑定手机
            APIResponse apiResponse = APIResponse.OK(APIResponseCodeEnum.BIND_PHONE.getCode());
            apiResponse.setData(APIResponseCodeEnum.BIND_PHONE.getCode());
            return apiResponse;
        }
        UserAccount userAccount = userAccountService.findUserByUserId(userData.getUserId());
        try {
            userAccountService.loadCommunityAuth(userAccount);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        User user = UserAssembler.assemblerUser(userAccount);
        sysActionLogService.loginLog(WebUtils.getClientIpAddr(request), userAccount.getId(), "微信登陆");
        return APIResponse.OK(user);
    }

    @ApiOperation("微信登陆-根据code获取access_token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "请求微信登陆获取的code", required = true)
    })
    @AnonAccess
    @GetMapping("/wechatLoginGetAccessToken")
    public APIResponse wechatLoginGetAccessToken(@RequestParam(required = true) String code) {
        String[] ss = WechatLoginUtil.getAccessToken(code);
        String accessToken = ss[0];
        if (StringUtils.isBlank(accessToken)) {
            return APIResponse.ERROR("获取失败");
        }
        String openid = ss[1];
        WechatAccessTokenCache.add(openid, accessToken);
        return APIResponse.OK(accessToken);
    }

    @AnonAccess
    @ApiOperation(value = "微信登陆后绑定手机号")
    @PostMapping("/bindMobile")
    public APIResponse<User> bindMobile(WechatLoginBindPhoneRequest wechatLoginBindPhoneRequest) {
        String mobile = wechatLoginBindPhoneRequest.getMobile();
        String smsVerifyCode = wechatLoginBindPhoneRequest.getSmsVerifyCode();
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(mobile)) {
            return APIResponse.ERROR("手机号或者验证码不能为空");
        }
        String openid = wechatLoginBindPhoneRequest.getOpendId();
        if (StringUtils.isBlank(openid)) {
            return APIResponse.ERROR("openId不能为空");
        }
        if (SmsVerifyCodeCache.isValid(mobile, smsVerifyCode)) {
            UserAccount userAccount = userAccountService.createUserByWxOpenid(openid, mobile, wechatLoginBindPhoneRequest.getNickName(), wechatLoginBindPhoneRequest.getAvatarUrl());
            userAccountService.loadCommunityAuth(userAccount);
            User user = UserAssembler.assemblerUser(userAccount);
            return APIResponse.OK(user);
        }
        return APIResponse.ERROR("验证码错误");
    }

    @ApiIgnore
    @AnonAccess
    @ApiOperation(value = "获取图形验证码")
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }

    @ApiIgnore
    @AnonAccess
    @ApiOperation(value = "校验图形验证码")
    @GetMapping("/captchaVerify")
    public APIResponse captchaVerify(@RequestParam String verifyCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CaptchaUtil.ver(verifyCode, request)) {
            // 清除session中的验证码
            CaptchaUtil.clear(request);
            return APIResponse.ERROR("验证码错误");
        }
        return APIResponse.OK();
    }

    @AnonAccess
    @ApiOperation(value = "发送短信验证码")
    @GetMapping("/sendSmsVerifyCode")
    public APIResponse sendSmsVerifyCode(@RequestParam String mobile) {
        if (SmsVerifyCodeLimitCache.canSend(mobile)) {
            String code = RandomUtils.generateNumberString(4);
            SmsVerifyCodeCache.put(mobile, code);
            String sign = sysConfigService.findValueFromCache("qcloud.sms.sign");
            String smsTempId = sysConfigService.findValueFromCache("qcloud.sms.temp.loginCode");
            try {
                if (SmsSendUtil.sendVerifyCode(sign, smsTempId,code, mobile, "5")){
                    SmsVerifyCodeCache.put(mobile, code);
                }
            } catch (ServiceException e) {
                return APIResponse.ERROR(e.getMessage());
            }
        } else {
            return APIResponse.ERROR("一分钟内只能发送一次");
        }
        return APIResponse.OK();
    }

}
