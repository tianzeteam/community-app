package com.smart.home.controller.pc.request.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/18
 **/
@Data
@ToString
public class SysConfigDTO {

    @ApiModelProperty(value = "主键ID, 更新必填", required = false)
    private Integer id;
    @ApiModelProperty(value = "参数键",required = true)
    private String paramKey;
    @ApiModelProperty(value = "参数值",required = true)
    private String paramValue;
    @ApiModelProperty(value = "备注", required = false)
    private String remark;

}
