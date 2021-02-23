package com.smart.home.common.enums;

/**
 * @author jason
 * @date 2021/2/23
 **/
public enum YesNoEnum {

    NO(0),YES(1);

    private int code;

    YesNoEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
