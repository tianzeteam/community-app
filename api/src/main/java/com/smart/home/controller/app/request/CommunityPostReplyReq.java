package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("帖子评论params")
@Data
public class CommunityPostReplyReq {

    @ApiModelProperty("帖子id")
    private Long id;

    @ApiModelProperty("帖子作者id")
    private Long authorId;

    @ApiModelProperty("评论内容")
    private String contents;
}
