package com.smart.home.controller.app.response.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CommunityInfoVo {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("简介")
    private String remark;

    @ApiModelProperty("图片")
    private String coverImage;

    @ApiModelProperty("是否加入过")
    private Boolean ifJoin = false;


}
