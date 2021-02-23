package com.smart.home.controller.app.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/23
 **/
@Data
@ToString
public class UserPrivacyVO {

    @ApiModelProperty("分类，0动态隐私设置，1个人主页隐私设置")
    private Integer privacyType;
    @ApiModelProperty("名称")
    private String title;

}
