package com.smart.home.controller.pc.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Data
@ToString
public class StaticPageUpdateDTO extends StaticPageCreateDTO {

    @ApiModelProperty("主键id")
    @NotNull(message = "主键id不能为空")
    private Integer id;

}
