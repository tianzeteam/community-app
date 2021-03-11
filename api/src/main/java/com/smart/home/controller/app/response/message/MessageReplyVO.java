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
    @ApiModelProperty("谁回复了我-用户头像")
    private String headUrl;
    @ApiModelProperty("谁回复了我-用户昵称")
    private String nickName;
    @ApiModelProperty("发生时间")
    private Date createdTime;
    @ApiModelProperty("回复内容分类:0回复文章评论1回复产品评价2回复帖子3回复回帖5评论文章")
    private Integer replyCategory;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("原文主键id")
    private Long sourceId;

}
