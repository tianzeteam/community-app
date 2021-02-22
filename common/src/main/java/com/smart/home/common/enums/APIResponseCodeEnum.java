package com.smart.home.common.enums;

/**
 * @author jason
 */
public enum APIResponseCodeEnum {

    SUCCESS(0,"成功"),
    FAILED(1,"失败"),
    ERROR_DUPLICATE_DATA(11, "重复记录错误"),
    ERROR_SIGN(12, "签名错误"),
    ERROR_SERVICE_BUSY(13, "服务繁忙"),
    ERROR_INVALID_DATA(14, "参数校验失败"),
    ERROR_NO_RECORDS(15, "记录不存在"),
    SYSTEM_ERROR(500,"系统异常"),
    DATA_ERROR(9999,"数据异常"),
    IO_ERROR(8888,"读写文件异常"),
    NO_AUTH(401,"未授权"),
    NO_PERMIT(98, "无权限");

    private Integer code;
    private String message;

    APIResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
