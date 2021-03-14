package com.smart.home.controller.pc.response.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel
@Data
public class CommunityUserMappingVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("社区id")
    private Integer communityId;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("社区名")
    private String communityTitle;

    @ApiModelProperty("社区备注")
    private String remark;

    @ApiModelProperty("社区排序")
    private Integer sort;

    @ApiModelProperty("是否置顶")
    private Integer topFlag;



}
