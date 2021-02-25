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
public class MyFollowerVO {

    @ApiModelProperty("头像地址")
    private String headUrl;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("签名/状态")
    private String sign;
    @ApiModelProperty("粉丝用户主键ID")
    private Long followerUserId;
    @ApiModelProperty("是否互相关注：0否1是")
    private Integer followEach;

}
