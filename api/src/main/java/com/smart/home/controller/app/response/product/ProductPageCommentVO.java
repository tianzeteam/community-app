package com.smart.home.controller.app.response.product;

import com.smart.home.dto.UserBaseInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jason
 * @date 2021/2/25
 **/
@ApiModel("产品页面的评价列表")
@Data
@ToString
public class ProductPageCommentVO extends UserBaseInfo {

    @ApiModelProperty("评价星等")
    private BigDecimal starCount;
    @ApiModelProperty("评价时间")
    private Date createdTime;
    @ApiModelProperty("评价内容")
    private String details;

}
