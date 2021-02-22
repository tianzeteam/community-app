package com.smart.home.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/17
 **/
@Data
@ToString
@NoArgsConstructor
@ApiModel( value = "APIResponse", description = "通用接口返回模型")
public class APIResponse<T> {

    @ApiModelProperty("返回码,0代表成功，其他代表失败")
    private int code;
    @ApiModelProperty("返回消息")
    private String msg;
    @ApiModelProperty(value = "返回数据", name = "data", dataType = "object")
    private T data;
    @ApiModelProperty("请求ID，方便日志排查")
    private String traceId;

    public APIResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public APIResponse<T> withTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public static <T> APIResponse<T> OK() {
        return new APIResponse<T>(0, null, null);
    }

    public static <T> APIResponse<T> OK(T data) {
        return new APIResponse<T>(0, null, data);
    }

    public static <T> APIResponse<T> ERROR(String msg) {
        return new APIResponse<T>(1, msg, null);
    }

    public static <T> APIResponse<T> ERROR(String msg, T data) {
        return new APIResponse<T>(1, msg, data);
    }

    public static <T> APIResponse<T> ERROR(int code, String msg) {
        return new APIResponse<T>(code, msg, null);
    }

    public static <T> APIResponse<T> ERROR(int code, String msg, T data) {
        return new APIResponse<T>(code, msg, data);
    }

}
