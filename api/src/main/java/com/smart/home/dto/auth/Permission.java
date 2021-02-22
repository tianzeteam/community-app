package com.smart.home.dto.auth;

import java.io.Serializable;

/**
 * 权限
 */
public class Permission implements Serializable {

    private String name;

    private String url;

    private String perms;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }
}
