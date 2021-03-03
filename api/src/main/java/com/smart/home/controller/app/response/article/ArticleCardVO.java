package com.smart.home.controller.app.response.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/3/3
 **/
@ApiModel("文章卡片")
@Data
@ToString
public class ArticleCardVO {

    @ApiModelProperty("文章主键id")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("来源")
    private String sourceFrom;
    @ApiModelProperty("创建时间")
    private Date createdTime;

}
