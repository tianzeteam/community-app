package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/3/4
 **/
public enum AutoAuditFlagEnum {

    WAIT_AUDIT(0, "等待机审核"),
    APPROVE(1, "机审通过"),
    IMAGE_EXCEPTION(2, "机审图片异常"),
    CONTENT_EXCEPTION(3, "机审文本异常"),
    IMAGE_AND_CONTENT_EXCEPTION(4, "机审图片文本都异常"),
    ERROR(9, "机审请求发生错误");

    private int code;
    private String desc;

    AutoAuditFlagEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
