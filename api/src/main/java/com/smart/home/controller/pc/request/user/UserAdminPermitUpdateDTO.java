package com.smart.home.controller.pc.request.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Data
@ToString
public class UserAdminPermitUpdateDTO {

    @ApiModelProperty("用户主键id")
    private Long id;
    @ApiModelProperty("权限设置列表(权限标识,是否勾选：0否1是)")
    private Map<String, Integer> permits;

}
