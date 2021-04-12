package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/2/26
 **/
public enum MessageTypeEnum {

    REPLY_ME(0, "回复我的"), LIKE(1, "赞"), NOTIFY(2, "系统通知"), MESSAGE(3, "私信");

    private int type;
    private String desc;

    MessageTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }
}
