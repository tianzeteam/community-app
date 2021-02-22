package com.smart.home.common.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/22
 **/
@Data
@ToString
public class TraceLogInfo {

    private String ip;
    private String url;
    private String httpMethod;
    private String classMethod;
    private Object requestParams;

}
