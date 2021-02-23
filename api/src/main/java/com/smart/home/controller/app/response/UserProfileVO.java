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
public class UserProfileVO {

    @ApiModelProperty("头像地址")
    private String headUrl;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("签名")
    private String sign;
    @ApiModelProperty("用户ID")
    private Long userId;

}
