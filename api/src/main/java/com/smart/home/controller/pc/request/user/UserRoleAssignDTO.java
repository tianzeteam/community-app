package com.smart.home.controller.pc.request.user;

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
public class UserRoleAssignDTO {

    @NotNull(message = "userId 不能为空")
    @ApiModelProperty(value = "用户主键ID", required = true)
    private Long userId;
    @NotEmpty(message = "roleIdList 不能为空")
    @ApiModelProperty(value = "角色主键ID列表", required = true)
    private List<Integer> roleIdList;

}
