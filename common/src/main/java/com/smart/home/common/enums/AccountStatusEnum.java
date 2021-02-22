package com.smart.home.common.enums;

/**
 * @author jason
 * 0:正常,1:暂停,2:锁住
 */
public enum AccountStatusEnum {

    NORMAL(0),
    PAUSED(1),
    LOCKED(2);

    private int status;

    AccountStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
