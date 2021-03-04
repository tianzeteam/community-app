package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class ProductCommentReplyCreateDTO {

    @ApiModelProperty("产品评价主键id")
    private Long productCommentId;
    @ApiModelProperty("父级回复主键id， 一级传0")
    private Long pid;
    @ApiModelProperty("评价内容")
    @NotBlank(message = "评价内容不能为空")
    @Size(max = 100, message = "评价内容不得超过100字")
    private String details;

}
