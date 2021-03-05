package com.smart.home.controller.pc.request.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/20
 **/
@Data
@ToString
public class SysRoleMenuAssignDTO {

    @NotNull(message = "roleId 不能为空")
    @ApiModelProperty(value = "角色主键ID", required = true)
    private Integer roleId;
    @NotEmpty(message = "menuIdList 不能为空")
    @ApiModelProperty(value = "菜单主键ID列表", required = true)
    private List<Integer> menuIdList;

}
