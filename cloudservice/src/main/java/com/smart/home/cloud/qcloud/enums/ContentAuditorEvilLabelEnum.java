package com.smart.home.cloud.qcloud.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author jason
 * @date 2021/3/4
 **/
public enum ContentAuditorEvilLabelEnum {

    Normal("Normal", "正常"),
    Polity("Polity", "涉政"),
    Porn("Porn", "色情"),
    Illegal("Illegal", "违法"),
    Terror("Terror", "暴恐"),
    Ad("Ad", "广告"),
    Custom("Custom", "自定义关键词");

    private String code;
    private String desc;
    ContentAuditorEvilLabelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ContentAuditorEvilLabelEnum asOf(String code) {
        for (ContentAuditorEvilLabelEnum value : ContentAuditorEvilLabelEnum.values()) {
            if (StringUtils.equals(code, value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
