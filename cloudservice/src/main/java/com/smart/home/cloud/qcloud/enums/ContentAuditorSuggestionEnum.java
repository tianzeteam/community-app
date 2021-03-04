package com.smart.home.cloud.qcloud.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author jason
 * @date 2021/3/4
 **/
public enum ContentAuditorSuggestionEnum {

    Block("Block", "打击"),
    Review("Review", "待复审"),
    Normal("Normal", "正常");

    private String code;
    private String desc;

    ContentAuditorSuggestionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static ContentAuditorSuggestionEnum asOf(String code) {
        for (ContentAuditorSuggestionEnum value : ContentAuditorSuggestionEnum.values()) {
            if (StringUtils.equals(code, value.getCode())) {
                return value;
            }
        }
        return null;
    }

}
