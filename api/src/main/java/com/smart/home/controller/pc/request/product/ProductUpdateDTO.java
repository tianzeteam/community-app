package com.smart.home.controller.pc.request.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 * @date 2021/3/1
 **/
@Data
@ToString
public class ProductUpdateDTO extends ProductCreateDTO {

    @ApiModelProperty("产品主键id")
    @NotNull(message = "产品主键id不能为空")
    private Integer id;

}
