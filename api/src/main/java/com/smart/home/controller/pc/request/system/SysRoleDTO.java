package com.smart.home.controller.pc.request.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/19
 **/
@Data
@ToString
public class SysRoleDTO {

    @ApiModelProperty("主键ID， 更新必填")
    private Integer id;
    @ApiModelProperty(value = "角色编码", required = true)
    private String code;
    @ApiModelProperty(value = "角色名称", required = true)
    private String name;
    @ApiModelProperty(value = "角色备注", required = false)
    private String remark;

}
