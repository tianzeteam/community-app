package com.smart.home.enums;

/**
 * @author jason
 * @date 2021/2/26
 **/
public enum ArticleStateEnum {

    DRAFT(0),PUBLISH(1);

    private int state;

    ArticleStateEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

}
