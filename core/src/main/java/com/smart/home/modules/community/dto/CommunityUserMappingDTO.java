package com.smart.home.modules.community.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommunityUserMappingDTO {
    private Long id;

    private Long userId;

    private Integer communityId;

    private Date createdTime;

    private String communityTitle;

    private String remark;

    private Integer sort;

    private Integer topFlag;



}
