package com.smart.home.controller.app.response.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/3
 **/
@Data
@ToString
public class SubjectCardVO {

    @ApiModelProperty("页面主键id")
    private Integer id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("链接地址")
    private String url;
    @ApiModelProperty("插入位置")
    private Integer locations;

}
