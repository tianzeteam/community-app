package com.smart.home.controller.app.response.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("帖子详情")
@Data
public class CommunityPostDetailVO {

    @ApiModelProperty("帖子主键id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;
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

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("正文")
    private String contents;

    @ApiModelProperty("图片列表")
    private List<String> imagesList;

    @ApiModelProperty("是否置顶0否1是")
    private Integer topFlag;

    @ApiModelProperty("是否加精0否1是")
    private Integer boutiqueFlag;

    @ApiModelProperty("赞数量")
    private Integer likeCount;

    @ApiModelProperty("是否能评论0否1是")
    private Integer commentFlag;

    @ApiModelProperty("被举报数量")
    private Integer reportCount = 0;

    @ApiModelProperty("收藏数量")
    private Integer collectCount;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("被踩数量")
    private Integer stampCount;

    /**
     * 回复数量
     */
    @ApiModelProperty("回复数量")
    private Integer replyCount = 0;


    /**
     * 分享数量
     */
    @ApiModelProperty("分享数量")
    private Integer shareCount = 0;
    /**
     * 浏览数量
     */
    @ApiModelProperty("浏览数量")
    private Long visitCount;

    @ApiModelProperty("简介")
    private String remark;

    //是否被当前用户收藏
    @ApiModelProperty("是否被当前用户收藏")
    private Integer collectFlag;

    //是否被当前用户点踩
    @ApiModelProperty("是否被当前用户点赞")
    private Integer likeFlag;

    @ApiModelProperty("是否被当前用户点踩")
    private Integer stampFlag;

    //是否被当前用户关注
    @ApiModelProperty("是否被当前用户关注")
    private Integer focusFlag;

    @ApiModelProperty("全部回复数")
    private Integer allReplyCount;

    @ApiModelProperty("分享页面")
    private String href;

}
