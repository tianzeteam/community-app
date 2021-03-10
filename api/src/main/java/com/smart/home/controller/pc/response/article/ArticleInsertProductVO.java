package com.smart.home.controller.pc.response.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/10
 **/
@ApiModel("投稿时插入的产品")
@Data
@ToString
public class ArticleInsertProductVO {

    @ApiModelProperty("产品主键id")
    private Integer id;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品简介")
    private String remark;

}
