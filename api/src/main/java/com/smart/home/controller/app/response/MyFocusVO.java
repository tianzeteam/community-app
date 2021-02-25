package com.smart.home.controller.app.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class MyFocusVO {

    @ApiModelProperty("头像地址")
    private String headUrl;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("签名/状态")
    private String sign;
    @ApiModelProperty("关注用户主键ID")
    private Long focusUserId;

}
