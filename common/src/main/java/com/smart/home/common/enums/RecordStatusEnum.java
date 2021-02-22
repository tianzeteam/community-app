package com.smart.home.common.enums;

/**
 * @author jason
 * @date 2021/2/20
 **/
public enum RecordStatusEnum {

    NORMAL(0, "正常"),
    PAUSED(1, "暂停");

    private int status;
    private String desc;

    RecordStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

}
