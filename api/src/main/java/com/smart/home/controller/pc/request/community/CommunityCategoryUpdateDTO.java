package com.smart.home.controller.pc.request.community;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class CommunityCategoryUpdateDTO extends CommunityCategoryCreateDTO {

    @ApiModelProperty("主键id")
    private Integer id;

}
