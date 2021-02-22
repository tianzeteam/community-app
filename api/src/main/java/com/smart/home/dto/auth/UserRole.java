package com.smart.home.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 用户角色
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {

    @ApiModelProperty("用户主键ID")
    private Long userId;
    @ApiModelProperty("角色主键ID")
    private Integer roleId;
    @ApiModelProperty("角色编码")
    private String roleCode;

}
