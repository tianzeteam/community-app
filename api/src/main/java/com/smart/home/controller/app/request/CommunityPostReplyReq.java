package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel("帖子评论params")
@Data
public class CommunityPostReplyReq {

    @ApiModelProperty("帖子id")
    @NotNull(message = "帖子id不能为空")
    private Long id;

    @ApiModelProperty("评论内容")
    @NotNull(message = "评论内容不能为空")
    @Size(max = 200, message = "评论内容不能超过200字")
    private String contents;
}
