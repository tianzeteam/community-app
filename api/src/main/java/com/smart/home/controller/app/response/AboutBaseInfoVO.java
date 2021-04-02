package com.smart.home.controller.app.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/4/2
 **/
@Data
@ToString
public class AboutBaseInfoVO {

    @ApiModelProperty("app版本号")
    private String appVersion;
    @ApiModelProperty("app名称")
    private String appName;
    @ApiModelProperty("app的logo地址")
    private String appLogoUrl;

}
