package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/2/24
 * 有趣分类
 **/
public enum FunCategoryEnum {

    PRODUCT_COMMENT(2, "产品评价"),
    PRODUCT_REPLY(3, "产品评价的回复");

    private int code;
    private String desc;

    FunCategoryEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

}
