package com.smart.home.enums;

/**
 *
 */
public enum EsSaveTypeEnum {

    ARTICLE(1, "文章"),
    COMMUNITY_POST(2, "帖子"),
    PRODUCT(3, "产品"),
    PRODUCT_COMMENT(4, "产品评价");

    private int type;
    private String desc;

    EsSaveTypeEnum(int type, String desc) {
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
