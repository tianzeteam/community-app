package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/2/26
 **/
public enum ArticleCategoryEnum {

    CONTENT(0, "文章"),VIDEO(1, "视频");

    private int code;
    private String desc;

    ArticleCategoryEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

}
