package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/2/24
 * 踩分类
 **/
public enum StampCategoryEnum {

    ARTICLE(0, "文章"),
    // 文章评论暂时没有
    //ARTICLE_COMMENT(1, "文章评论"),
    PRODUCT_COMMENT(2, "产品评价"),
    PRODUCT_REPLY(3, "产品评价的回复"),
    POST(4, "主贴"),
    POST_REPLY(5, "主贴评论");

    private int code;
    private String desc;

    StampCategoryEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

}