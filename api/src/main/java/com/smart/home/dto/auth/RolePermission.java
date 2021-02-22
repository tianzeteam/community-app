package com.smart.home.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 角色权限
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RolePermission {

    private Long roleId;

    private Long permissionId;

    private List<Long> permIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public List<Long> getPermIds() {
        return permIds;
    }

    public void setPermIds(List<Long> permIds) {
        this.permIds = permIds;
    }
}
