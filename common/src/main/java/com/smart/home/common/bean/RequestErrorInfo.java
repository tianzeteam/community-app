package com.smart.home.common.bean;

/**
 * @author jason
 * @date 2021/2/22
 **/
public class RequestErrorInfo extends TraceLogInfo{

    private RuntimeException exception;

    public RuntimeException getException() {
        return exception;
    }

    public void setException(RuntimeException exception) {
        this.exception = exception;
    }

}
