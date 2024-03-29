package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/3/11
 **/
public enum MessageSubTypeEnum {

    ARTICLE_COMMENT(0, "文章评论"),
    PRODUCT_COMMENT(1, "产品评价"),
    COMMUNITY_POST(2, "帖子"),
    COMMUNITY_POST_REPLY(3, "回帖"),
    ARTICLE(5, "文章");

    private int code;
    private String desc;

    MessageSubTypeEnum(int code, String desc) {
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
