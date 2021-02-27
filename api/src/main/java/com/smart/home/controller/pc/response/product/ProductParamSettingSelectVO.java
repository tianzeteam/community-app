package com.smart.home.controller.pc.response.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Data
@ToString
public class ProductParamSettingSelectVO {

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("名称")
    private String paramName;
    @ApiModelProperty("默认值")
    private String defaultValue;
    @ApiModelProperty("简介")
    private String remark;

}
