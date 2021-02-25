package com.smart.home.controller.app.response.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author jason
 * @date 2021/2/25
 **/
@ApiModel("产业页面的评价卡片的返回对象")
@Data
@ToString
public class ProductPageCommentTabHeadVO {

    @ApiModelProperty("综合评分")
    private BigDecimal averageScore;
    @ApiModelProperty("一星数量")
    private Integer oneStarCount;
    @ApiModelProperty("二星数量")
    private Integer twoStarCount;
    @ApiModelProperty("三星数量")
    private Integer threeStarCount;
    @ApiModelProperty("四星数量")
    private Integer fourStarCount;
    @ApiModelProperty("五星数量")
    private Integer fiveStarCount;

}
