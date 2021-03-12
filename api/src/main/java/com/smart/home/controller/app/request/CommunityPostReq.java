package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class CommunityPostReq {

    @ApiModelProperty("帖子id")
    private Long id;

    @ApiModelProperty("数组list，可作为多原因勾选")
    private List<String> list;
}
