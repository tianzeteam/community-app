package com.smart.home.controller.app.response.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/3
 **/
@ApiModel("草稿箱")
@Data
@ToString
public class MyDraftArticleVO {

    @ApiModelProperty("文章主键id")
    private Long id;
    @ApiModelProperty("上次编辑时间")
    private Date updatedDate;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("正文")
    private String details;
    @ApiModelProperty("图片")
    private List<String> imageList;

}
