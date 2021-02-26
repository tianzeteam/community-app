package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/2/26
 **/
public enum AgreementTypeEnum {

    ARTICLE_CREATE_RULE(50, "内容创作规范"),
    ARTICLE_CREATE_AGREEMENT(51, "文章投稿协议");

    private int type;
    private String desc;

    AgreementTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
