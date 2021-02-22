package com.smart.home.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户
 */
@Data
@ToString
public class User implements Serializable {

    @ApiModelProperty("用户主键ID")
    private Long id;

    @ApiModelProperty("用户头像")
    private String headUrl;

    @ApiModelProperty("用户用户名")
    private String username;

    @ApiModelProperty("用户手机号码")
    private String mobile;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("访问令牌，后续访问需要放在header中的Authorization参数中")
    private String accessToken;

}
