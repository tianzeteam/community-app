package com.smart.home.controller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smart.home.assembler.UserAssembler;
import com.smart.home.cache.SmsVerifyCodeCache;
import com.smart.home.cache.SmsVerifyCodeLimitCache;
import com.smart.home.common.enums.APIResponseCodeEnum;
import com.smart.home.dto.APIResponse;
import com.smart.home.common.util.RandomUtils;
import com.smart.home.dto.auth.User;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.entity.UserData;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.user.service.UserDataService;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @AnonAccess
    @ApiOperation(value = "用户名密码登陆")
    @PostMapping("loginByPassword")
    public APIResponse<User> login(@RequestParam String username, @RequestParam String password) {
        UserAccount userAccount = userAccountService.doAuthentication(username, password);
        User user = UserAssembler.assemblerUser(userAccount);
        return APIResponse.OK(user);
    }

    @AnonAccess
    @ApiOperation(value = "手机验证码登陆")
    @PostMapping("/loginBySmsCode")
    public APIResponse<User> loginBySmsCode(@RequestParam String mobile, @RequestParam String smsVerifyCode) {
        if (SmsVerifyCodeCache.isValid(mobile, smsVerifyCode)) {
            UserAccount userAccount = userAccountService.loginViaMobile(mobile);
            User user = UserAssembler.assemblerUser(userAccount);
            return APIResponse.OK(user);
        }
        return APIResponse.ERROR("验证码错误");
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
     *
     * @param data
     * @return
     */
    @AnonAccess
    @ApiOperation(value = "微信登陆,返回0正常登陆，返回2需要进一步绑定手机", notes = "返回0正常登陆，返回2需要进一步绑定手机")
    @PostMapping("/loginByWechat")
    public APIResponse loginByWechat(@RequestBody String data) {
        JSONObject jsonObject = JSON.parseObject(data);
        String wx = jsonObject.getString("openId");
        UserData userData = userDataService.findByWxOpenid(wx);
        if (Objects.isNull(userData)) {
            // 说明还没微信登陆过，需要绑定手机
            APIResponse apiResponse = APIResponse.ERROR(APIResponseCodeEnum.BIND_PHONE.getCode(), "需要进行手机绑定");
            apiResponse.setData(wx);
            return apiResponse;
        }
        UserAccount userAccount = userAccountService.findUserByUserId(userData.getUserId());
        User user = UserAssembler.assemblerUser(userAccount);
        return APIResponse.OK(user);
    }

    @AnonAccess
    @ApiOperation(value = "微信登陆后绑定手机号")
    @PostMapping("/bindMobile")
    public APIResponse<User> bindMobile(@RequestParam String openid, @RequestParam String mobile, @RequestParam String smsVerifyCode) {
        if (SmsVerifyCodeCache.isValid(mobile, smsVerifyCode)) {
            UserAccount userAccount = userAccountService.createUserByWxOpenid(openid, mobile);
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
            // TODO 调用云服务发送短信, 遵循1分钟1一次，一小时内4次，一天内8次的原则
            code = "123456";
            SmsVerifyCodeCache.put(mobile, code);
            return APIResponse.OK();
        } else {
            return APIResponse.ERROR("一分钟内只能发送一次");
        }
    }

}
