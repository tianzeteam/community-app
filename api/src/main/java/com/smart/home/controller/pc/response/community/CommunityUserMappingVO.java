package com.smart.home.controller.pc.response.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

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


}
