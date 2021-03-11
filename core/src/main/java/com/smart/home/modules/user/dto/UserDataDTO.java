package com.smart.home.modules.user.dto;

import lombok.Data;

@Data
public class UserDataDTO {

    //头像
    private String headUrl;
    //昵称
    private String username;

    //-------------

    private Long id;

    private Long userId;

    private String wxOpenid;

    private String sign;

    private String remark;

    private Integer userLevel;

    private Integer userLevelCount;

    private Integer focusCount;

    private Integer followCount;

    private Integer likeCount;

    private Integer postCount;

    private Integer commentCount;

    private Integer replyCount;

    private Integer evaluateCount;

    private Integer contributeCount;
}
