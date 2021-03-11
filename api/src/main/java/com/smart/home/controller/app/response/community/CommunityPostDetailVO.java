package com.smart.home.controller.app.response.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("帖子详情")
@Data
public class CommunityPostDetailVO {

    //头像
    @ApiModelProperty("")
    private String headUrl;
    //昵称
    @ApiModelProperty("")
    private String nickname;
    //用户等级
    @ApiModelProperty("")
    private Integer userLevel;

    @ApiModelProperty("")
    private String userRemark;
    //社区名
    @ApiModelProperty("")
    private String communityTitle;
    ///////////////////////////

    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("")
    private Integer community;

    @ApiModelProperty("")
    private Long userId;

    @ApiModelProperty("")
    private String title;

    @ApiModelProperty("")
    private String contents;

    @ApiModelProperty("")
    private List<String> imagesList;

    @ApiModelProperty("")
    private Integer topFlag;

    @ApiModelProperty("")
    private Integer boutiqueFlag;

    @ApiModelProperty("")
    private Integer likeCount;

    @ApiModelProperty("")
    private Integer commentFlag;

    @ApiModelProperty("")
    private Integer reportCount;

    @ApiModelProperty("")
    private Integer collectCount;

    @ApiModelProperty("")
    private Integer auditStatus;

    @ApiModelProperty("")
    private Integer revision;

    @ApiModelProperty("")
    private Date createdTime;

    @ApiModelProperty("")
    private Integer stampCount;

    @ApiModelProperty("")
    private Integer state;
    /**
     * 回复数量
     */
    @ApiModelProperty("")
    private Integer replyCount;

    @ApiModelProperty("")
    private Integer autoAuditFlag;

    @ApiModelProperty("")
    private Integer hitSensitiveCount;

    /**
     * 分享数量
     */

    @ApiModelProperty("")
    private Integer shareCount;
    /**
     * 浏览数量
     */
    @ApiModelProperty("")
    private Long visitCount;

    @ApiModelProperty("")
    private String remark;

    //是否被当前用户收藏
    @ApiModelProperty("")
    private Integer collectFlag;

    //是否被当前用户点踩
    @ApiModelProperty("")
    private Integer likeFlag;

    @ApiModelProperty("")
    private Integer stampFlag;

    //是否被当前用户关注
    @ApiModelProperty("")
    private Integer focusFlag;

}
