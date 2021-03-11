package com.smart.home.cloud.qcloud.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author jason
 * @date 2021/3/3
 **/
public enum ImageAuditorLabelEnum {

    Normal("Normal","正常"),
    Porn("Porn", "色情"),
    Abuse("Abuse", "谩骂"),
    Ad("Ad", "广告"),
    Custom("Custom", "自定义图片");

    private String code;
    private String desc;

    ImageAuditorLabelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ImageAuditorLabelEnum asOf(String label) {
        for (ImageAuditorLabelEnum value : ImageAuditorLabelEnum.values()) {
            if(StringUtils.equals(value.getCode(), label)) {
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
