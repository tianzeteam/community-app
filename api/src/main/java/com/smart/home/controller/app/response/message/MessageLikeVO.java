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
public class MessageLikeVO {

    @ApiModelProperty("消息主键id")
    private Long id;

    @ApiModelProperty("谁点赞了我-用户主键id")
    private Long userId;
    @ApiModelProperty("谁点赞了我-用户头像")
    private String headUrl;
    @ApiModelProperty("谁点赞了我-用户昵称")
    private String nickName;
    @ApiModelProperty("发生时间")
    private Date createdTime;
    @ApiModelProperty("点赞内容分类:0文章评论1产品评价2帖子3回帖5文章")
    private Integer likeCategory;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("原文主键id")
    private Long sourceId;

}
