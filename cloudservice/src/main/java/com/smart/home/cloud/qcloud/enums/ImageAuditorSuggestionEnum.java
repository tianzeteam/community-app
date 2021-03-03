package com.smart.home.cloud.qcloud.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author jason
 * @date 2021/3/3
 **/
public enum ImageAuditorSuggestionEnum {

    Block("Block", "建议屏蔽"),
    Review("Review", "Review"),
    Pass("Pass", "建议通过");

    private String code;
    private String desc;

    ImageAuditorSuggestionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ImageAuditorSuggestionEnum asOf(String suggestion) {
        for (ImageAuditorSuggestionEnum value : ImageAuditorSuggestionEnum.values()) {
            if (StringUtils.equals(value.getCode(), suggestion)) {
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

}
