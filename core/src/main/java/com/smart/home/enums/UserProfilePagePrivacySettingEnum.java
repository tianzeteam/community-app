package com.smart.home.enums;

import java.util.ArrayList;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/25
 * 用户个人页隐私设置分类
 * 粉丝在个人主页可以看到的勾选的动态
 **/
public enum UserProfilePagePrivacySettingEnum {

    POST(0, "发帖"),
    ARTICLE_COMMENT(1, "投稿评论"),
    POST_REPLY(2, "帖子回复"),
    PRODUCT_COMMENT(3, "产品评价"),
    ARTICLE(4, "投稿");

    private int code;
    private String desc;

    UserProfilePagePrivacySettingEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public List<Integer> getCodeList() {
        List<Integer> list = new ArrayList<>();
        for (UserProfilePagePrivacySettingEnum value : UserProfilePagePrivacySettingEnum.values()) {
            list.add(value.getCode());
        }
        return list;
    }

}
