package com.smart.home.controller.app.response.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("帖子评论")
@Data
public class CommunityPostReplyVO {

    @ApiModelProperty("评论人的头像")
    private String headUrl;

    @ApiModelProperty("评论人的昵称")
    private String nickName;

    @ApiModelProperty("评论人的等级")
    private Integer userLevel;
    @ApiModelProperty("给谁回复")
    private String toUserNickName;
    ////////////////////////
    @ApiModelProperty("评论id")
    private Long id;


    @ApiModelProperty("帖子id")
    private Long postId;

    @ApiModelProperty("回复id")
    private Long postReplyId;

    @ApiModelProperty("回复类型，0主贴，1回复")
    private Integer replyType;

    @ApiModelProperty("内容")
    private String contents;

    @ApiModelProperty("赞数量")
    private Integer likeCount;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("踩数量")
    private Integer stampCount;

    @ApiModelProperty("机审标记:0还未机审1机审通过2机审图片异常3机审文本异常")
    private Integer autoAuditFlag;
    @ApiModelProperty("审核标记：0待审核1审核通过2审核不通过")
    private Integer auditFlag;
    @ApiModelProperty("命中敏感词数量")
    private Integer hitSensitiveCount;
    @ApiModelProperty("用户投诉数量")
    private Integer reportCount;
    /**
     * 是否是作者：0否1是
     */
    @ApiModelProperty("是否是作者：0否1是")
    private Integer authorFlag;
    /**
     * 回复给谁：用户主键id
     */
    @ApiModelProperty("回复给谁：用户主键id")
    private Long toUserId;

}
