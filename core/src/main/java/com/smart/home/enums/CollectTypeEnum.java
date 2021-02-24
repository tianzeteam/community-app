package com.smart.home.enums;

import com.fasterxml.jackson.databind.type.CollectionType;

/**
 * @author jason
 * @date 2021/2/24
 * 收藏类型
 **/
public enum CollectTypeEnum {

    ARTICLE(0, "投稿"),POST(1, "帖子"),PRODUCT(2, "产品");

    private int type;
    private String desc;

    CollectTypeEnum(int code, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}
