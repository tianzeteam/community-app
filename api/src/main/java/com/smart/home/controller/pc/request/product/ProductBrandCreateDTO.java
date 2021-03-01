package com.smart.home.controller.pc.request.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/1
 **/
@Data
@ToString
public class ProductBrandCreateDTO {

    @ApiModelProperty("品牌logo")
    private String logo;
    @ApiModelProperty("品牌名称")
    private String brandName;
    @ApiModelProperty("品牌官网")
    private String url;
    @ApiModelProperty("排序，越大越靠前")
    private Integer sort;

}
