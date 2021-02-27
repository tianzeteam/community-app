package com.smart.home.controller.pc.response.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Data
@ToString
public class AdminPermissionVO {

    @ApiModelProperty("权限名称")
    private String permitName;
    @ApiModelProperty("权限名称")
    private String permitCode;

}
