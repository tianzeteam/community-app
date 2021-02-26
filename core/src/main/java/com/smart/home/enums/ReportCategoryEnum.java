package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/2/26
 * 举报分类
 **/
public enum ReportCategoryEnum {

    ARTICLE(0, "举报文章"), POST(1, "举报社区帖子");

    private int code;
    private String desc;

    ReportCategoryEnum(int code, String desc) {
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
