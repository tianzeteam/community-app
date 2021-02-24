package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/2/24
 * 审查分类
 **/
public enum AuditCategoryEnum {
    // 社区管理部分
    TOP(0, "置顶"),
    BOUTIQUE(1, "加精"),
    DELETE_POST(2, "删帖"),
    DELETE_REPLY(3, "删评论"),
    DELETE_IMAGE(4, "删图"),
    WARNING(5, "警告"),
    NO_SPEECH(6, "禁言"),
    CLOSE(7, "封禁"),
    // 内容审查部分
    IMAGE_AUDIT(8, "图片审查"),
    ARTICLE_AUDIT(9, "文章人工审查"),
    VIDEO_AUDIT(10, "视频人工审查"),
    CONTENT_AUDIT(11, "评论，帖子正文自动审查，敏感词覆盖+人工可复查");

    private int code;
    private String desc;

    AuditCategoryEnum(int code, String desc) {
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
