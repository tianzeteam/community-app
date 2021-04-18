package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/4/17
 *
 **/
@Data
@ToString
public class WechatLoginBindPhoneRequest extends WechatLoginRequest {

    @ApiModelProperty("手机号码")
    private String mobile;
    @ApiModelProperty("验证码")
    private String smsVerifyCode;

}
