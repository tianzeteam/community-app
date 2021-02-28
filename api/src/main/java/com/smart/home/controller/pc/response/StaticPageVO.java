package com.smart.home.controller.pc.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Data
@ToString
public class StaticPageVO {

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("名称")
    private String categoryName;
    @ApiModelProperty("链接地址")
    private String url;

}
