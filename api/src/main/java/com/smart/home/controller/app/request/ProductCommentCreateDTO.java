package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;


/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class ProductCommentCreateDTO {

    @ApiModelProperty("评价的产品主键id")
    private Integer productId;

    @ApiModelProperty("评价星等")
    private BigDecimal startCount;

    @ApiModelProperty("评价内容")
    @NotBlank(message = "评价内容不能为空")
    @Max(value = 100, message = "评价内容不得超过100字")
    private String details;

}
