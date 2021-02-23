package com.smart.home.controller.app.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/23
 **/
@Data
@ToString
public class HelpVO {

    @ApiModelProperty("主键ID")
    private Integer id;
    @ApiModelProperty("父级主键ID， 顶级为0")
    private Integer pid;
    @ApiModelProperty("标题/名称")
    private String title;
    @ApiModelProperty("详细内容，最后一级的内容")
    private String details;

}
