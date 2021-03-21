package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

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
    private BigDecimal starCount;

    @ApiModelProperty("评价内容")
    @NotBlank(message = "评价内容不能为空")
    private String details;
    @ApiModelProperty("图片数组")
    private List<String> imageList;

}
