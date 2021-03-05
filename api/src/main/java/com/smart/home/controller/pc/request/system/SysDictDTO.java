package com.smart.home.controller.pc.request.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/21
 **/
@Data
@ToString
public class SysDictDTO {

    @ApiModelProperty(value = "父主键ID， 顶级传0", required = true)
    private Integer pid;
    @ApiModelProperty(value = "字典类型：0键值对1树形结构", required = true)
    private Integer dictType;
    @ApiModelProperty(value = "字典编码", required = true)
    private String dictCode;
    @ApiModelProperty(value = "字典名称", required = true)
    private String dictName;
    @ApiModelProperty(value = "字典值", required = true)
    private String dictValue;
    @ApiModelProperty(value = "字典分组， 默认为0", required = false)
    private Integer dictGroup;

}
