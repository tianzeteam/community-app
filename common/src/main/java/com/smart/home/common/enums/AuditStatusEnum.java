package com.smart.home.common.enums;

/**
 * @author jason
 */
public enum AuditStatusEnum {

    WAIT_AUDIT(0),APPROVED(1),REJECT(2);

    private int code;

    AuditStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
