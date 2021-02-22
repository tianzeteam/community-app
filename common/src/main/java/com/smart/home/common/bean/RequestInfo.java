package com.smart.home.common.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/22
 **/
@Data
@ToString
public class RequestInfo extends TraceLogInfo {

    private Object result;
    private Long timeCost;

}
