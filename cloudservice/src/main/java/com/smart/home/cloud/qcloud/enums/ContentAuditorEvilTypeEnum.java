package com.smart.home.cloud.qcloud.enums;

/**
 * @author jason
 * @date 2021/3/4
 **/
public enum ContentAuditorEvilTypeEnum {

    NORMAL(100, "正常"),
    POLITICS(20001, "政治"),
    PORN(20002, "色情"),
    DRUG(20006, "涉毒违法"),
    ABUSE(20007, "谩骂"),
    AD(20105, "广告引流"),
    VIOLENT(24001, "暴恐"),
    OTHER(-1, "其他");

    private int code;
    private String desc;

    ContentAuditorEvilTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ContentAuditorEvilTypeEnum asOf(int code) {
        for (ContentAuditorEvilTypeEnum value : ContentAuditorEvilTypeEnum.values()) {
            if (code == value.getCode()) {
                return value;
            }
        }
        return ContentAuditorEvilTypeEnum.OTHER;
    }
}
