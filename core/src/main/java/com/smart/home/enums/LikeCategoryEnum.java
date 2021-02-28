package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/2/24
 * 赞分类
 **/
public enum LikeCategoryEnum {

    ARTICLE(0, "文章"),
    ARTICLE_COMMENT(1, "文章评论"),
    PRODUCT_COMMENT(2, "产品评价"),
    PRODUCT_REPLY(3, "产品评价的回复"),
    POST(4, "主贴"),
    POST_REPLY(5, "主贴评论");

    private int code;
    private String desc;

    LikeCategoryEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

}
