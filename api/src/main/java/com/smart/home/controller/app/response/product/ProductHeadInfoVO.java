package com.smart.home.controller.app.response.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author jason
 * @date 2021/3/20
 **/
@Data
@ToString
public class ProductHeadInfoVO {

    @ApiModelProperty("产品缩略图")
    private String coverImage;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品评价得分")
    private BigDecimal averageScore;

}
