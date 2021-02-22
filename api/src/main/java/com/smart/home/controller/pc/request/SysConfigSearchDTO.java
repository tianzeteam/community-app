package com.smart.home.controller.pc.request;

import com.smart.home.dto.RequestPageBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/19
 **/
@Data
@ToString
public class SysConfigSearchDTO extends RequestPageBean {

    @ApiModelProperty(value = "参数键", required = false)
    private String paramKey;

    @ApiModelProperty(value = "备注", required = false)
    private String remark;

}
