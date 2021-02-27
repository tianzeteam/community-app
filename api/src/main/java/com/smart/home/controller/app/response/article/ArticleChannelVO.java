package com.smart.home.controller.app.response.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Data
@ToString
public class ArticleChannelVO {

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("名称")
    private String title;

}
