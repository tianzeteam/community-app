package com.smart.home.controller.app.response.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ApiModel("推荐帖子")
@Data
public class RecommendCommunityPostVO {

    @ApiModelProperty("帖子主键id")
    private Long id;
    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("用户名")
    private String nickname;
    @ApiModelProperty("用户等级")
    private Integer userLevel;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("赞数量")
    private Integer likeCount;
    @ApiModelProperty("回复数量")
    private Integer replyCount;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("图片数组")
    private List<String> imagesList;

    //社区名
    @ApiModelProperty("社区名")
    private String communityTitle;
    ///////////////////////////

    @ApiModelProperty("社区id")
    private Integer community;

    @ApiModelProperty("是否置顶，0否1是")
    private Integer topFlag;

    @ApiModelProperty("是否加精")
    private Integer boutiqueFlag;

    @ApiModelProperty("是否能评论")
    private Integer commentFlag;

    @ApiModelProperty("被举报数量")
    private Integer reportCount;

    @ApiModelProperty("收藏数量")
    private Integer collectCount;

    @ApiModelProperty("审核状态，0待审核，1审核通过，2审核失败")
    private Integer auditStatus;

    @ApiModelProperty("踩数量")
    private Integer stampCount;

    @ApiModelProperty("状态，0草稿，1发布，2删除")
    private Integer state;

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

    @ApiModelProperty("简介，截取正文内容前N个字符")
    private String remark;

}
