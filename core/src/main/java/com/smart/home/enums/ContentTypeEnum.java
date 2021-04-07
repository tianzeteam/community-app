package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/3/5
 **/
public enum ContentTypeEnum {

    ARTICLE_COMMENT(0, "文章评论"),
    PRODUCT_COMMENT(1, "产品评价"),
    COMMUNITY_POST(2, "帖子"),
    COMMUNITY_POST_REPLY(3, "回帖"),
    ARTICLE_COMMENT_REPLY(4, "评论的回复"),
    PRODUCT_COMMENT_REPLY(5, "评价的回复");

    private int code;
    private String desc;

    ContentTypeEnum(int code, String desc) {
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
