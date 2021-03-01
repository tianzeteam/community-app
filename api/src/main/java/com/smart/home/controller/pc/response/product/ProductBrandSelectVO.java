package com.smart.home.controller.pc.response.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/1
 **/
@Data
@ToString
public class ProductBrandSelectVO {

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("logo")
    private String logo;
    @ApiModelProperty("名称")
    private String brandName;

}
