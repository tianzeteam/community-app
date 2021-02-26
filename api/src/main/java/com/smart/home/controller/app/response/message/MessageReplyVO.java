package com.smart.home.controller.app.response.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/2/26
 * 点赞列表
 **/
@Data
@ToString
public class MessageReplyVO {

    @ApiModelProperty("消息主键id")
    private Long id;

    @ApiModelProperty("谁回复了我-用户主键id")
    private Long userId;
    @ApiModelProperty("谁回复了我-用户昵称")
    private String nickName;
    @ApiModelProperty("发生时间")
    private Date createdTime;
    @ApiModelProperty("回复内容分类:0文章1文章评论")
    private Integer replyCategory;
    @ApiModelProperty("文章封面图片")
    private String coverImage;
    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("原文主键id")
    private Long sourceId;

}
