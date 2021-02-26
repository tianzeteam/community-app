package com.smart.home.controller.pc.request.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class ArticleChannelUpdateDTO extends ArticleChannelCreateDTO {

    @NotNull(message = "主键id不能为空")
    @ApiModelProperty("主键id")
    private Integer id;

}
