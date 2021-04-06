package com.smart.home.controller.pc.response.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class PcUserProfileVO {

    @ApiModelProperty("头像地址")
    private String headUrl;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("签名")
    private String sign;
    @ApiModelProperty("个人简介")
    private String remark;
    @ApiModelProperty("我的投稿数量")
    private Long articleCount;
    @ApiModelProperty("我的草稿数量")
    private Long draftArticleCount;

}
