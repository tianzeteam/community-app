package com.smart.home.controller.app.response.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/3
 **/
@ApiModel("推荐大图")
@Data
@ToString
public class ArticleBigImageCardVO {

    @ApiModelProperty("文章主键id")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("大图")
    private String coverImage;
    @ApiModelProperty("文章分类：0文章1ship ")
    private Integer category;

}
