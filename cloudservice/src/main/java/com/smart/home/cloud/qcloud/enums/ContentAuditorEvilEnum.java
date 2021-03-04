package com.smart.home.cloud.qcloud.enums;

/**
 * @author jason
 * @date 2021/3/4
 **/
public enum ContentAuditorEvilEnum {

    NORMAL(0, "正常"),EVIL(1, "可疑");

    private int code;
    private String desc;

    ContentAuditorEvilEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ContentAuditorEvilEnum asOf(int code) {
        for (ContentAuditorEvilEnum value : ContentAuditorEvilEnum.values()) {
            if (code == value.getCode()) {
                return value;
            }
        }
        return null;
    }

}
