package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/2/27
 **/
public enum ArticleRecommendTypeEnum {

    ARTCILE_CARD(0, "文章卡片"),
    BIG_IMAGE_CARD(1, "大图卡片"),
    BIG_IMAGE_TOP(2, "设置为大图并置顶");

    private int code;
    private String desc;

    ArticleRecommendTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
