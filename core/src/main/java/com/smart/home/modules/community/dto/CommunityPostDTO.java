package com.smart.home.modules.community.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommunityPostDTO {
    //头像
    private String headUrl;
    //昵称
    private String username;
    //用户等级
    private Integer userLevel;
    //社区名
    private String communityTitle;
    ///////////////////////////
    private Long id;

    private Integer community;

    private Long userId;

    private String title;

    private String contents;

    private List<String> imagesList;

    private Integer topFlag;

    private Integer boutiqueFlag;

    private Integer likeCount;

    private Integer commentFlag;

    private Integer reportCount;

    private Integer collectCount;

    private Integer auditStatus;

    private Integer revision;

    private Date createdTime;

    private Integer stampCount;

    private Integer state;
    /**
     * 回复数量
     */
    private Integer replyCount;
    private Integer autoAuditFlag;
    private Integer hitSensitiveCount;
    /**
     * 分享数量
     */
    private Integer shareCount;
    /**
     * 浏览数量
     */
    private Long visitCount;

    private String remark;
}
