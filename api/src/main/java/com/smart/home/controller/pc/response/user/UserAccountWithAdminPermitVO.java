package com.smart.home.controller.pc.response.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author jason
 * @date 2021/2/27
 **/
@ApiModel("带后台管理权限数据的用户")
@Data
@ToString
public class UserAccountWithAdminPermitVO {

    @ApiModelProperty("用户主键id")
    private Long id;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("后台权限列表")
    private Map<String, Map<String, Integer>> permissions;

}
