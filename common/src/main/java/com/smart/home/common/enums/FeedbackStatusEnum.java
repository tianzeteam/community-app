package com.smart.home.common.enums;

/**
 * @author jason
 * @date 2021/2/23
 * 用户反馈的状态0等待回复1已回复2已关闭
 **/
public enum FeedbackStatusEnum {

    WAIT_REPLY(0), REPLIED(1),CLOSED(2);

    private int status;

    FeedbackStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
