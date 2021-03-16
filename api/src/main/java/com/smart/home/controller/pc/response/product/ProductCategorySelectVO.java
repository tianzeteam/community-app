package com.smart.home.controller.pc.response.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class ProductCategorySelectVO {

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("名称")
    private String title;
    @ApiModelProperty("参数列表")
    private List<Integer> paramIdList;

}
