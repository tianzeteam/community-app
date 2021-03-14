package com.smart.home.controller.app.response.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
@ApiModel
@Data
public class CommunityDetailVO {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("社区id")
    private Integer communityId;
    @ApiModelProperty("创建时间")
    private Date createdTime;

    //头像
    @ApiModelProperty("头像")
    private String headUrl;
    //昵称
    @ApiModelProperty("昵称")
    private String nickname;
    //用户等级
    @ApiModelProperty("用户等级")
    private Integer userLevel;
    @ApiModelProperty("用户简介")
    private String userRemark;
    //社区名
    @ApiModelProperty("社区名")
    private String communityTitle;
    ///////////////////////////

    @ApiModelProperty("帖子标题")
    private String title;

    @ApiModelProperty("图片")
    private List<String> imagesList;

    @ApiModelProperty("是否置顶")
    private Integer topFlag;

    @ApiModelProperty("是否精品")
    private Integer boutiqueFlag;

    @ApiModelProperty("赞数量")
    private Integer likeCount;

    @ApiModelProperty("评论数量")
    private Integer commentFlag;

    @ApiModelProperty("被举报数量")
    private Integer reportCount;

    @ApiModelProperty("收藏数量")
    private Integer collectCount;

    @ApiModelProperty("审核状态0待审核1审核通过2审核失败")
    private Integer auditStatus;

    @ApiModelProperty("踩数量")
    private Integer stampCount;

    @ApiModelProperty("状态0草稿1发布2删除")
    private Integer state;
    /**
     * 回复数量
     */
    @ApiModelProperty("回复数量")
    private Integer replyCount;
    @ApiModelProperty("机审标记:0还未机审1机审通过2机审图片异常3机审文本异常")
    private Integer autoAuditFlag;
    @ApiModelProperty("命中敏感词数量")
    private Integer hitSensitiveCount;
    /**
     * 分享数量
     */
    @ApiModelProperty("分享数量")
    private Integer shareCount;
    /**
     * 浏览数量
     */
    @ApiModelProperty("浏览数量")
    private Long visitCount;

    @ApiModelProperty("帖子简介")
    private String remark;

}
