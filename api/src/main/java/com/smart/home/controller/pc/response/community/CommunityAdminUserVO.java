package com.smart.home.controller.pc.response.community;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/1
 **/
@Data
@ToString
public class CommunityAdminUserVO {

    @ApiModelProperty("用户主键id")
    private Long userId;
    @ApiModelProperty("用户昵称")
    private String nickName;

}
