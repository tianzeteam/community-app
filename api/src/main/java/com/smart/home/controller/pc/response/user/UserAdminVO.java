package com.smart.home.controller.pc.response.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/3/2
 **/
@Data
@ToString
public class UserAdminVO {

    @ApiModelProperty("用户主键id")
    private Long userId;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("头像地址")
    private String headUrl;
    @ApiModelProperty("用户简介")
    private String remark;
    @ApiModelProperty("注册时间")
    private Date createdTime;

    private UserDataAdminVO userDataAdminVO;

    @ApiModelProperty("用户当前状态")
    private String userCurrentStatus;
    @ApiModelProperty("解封时间")
    private Date effectiveEndDate;
}
