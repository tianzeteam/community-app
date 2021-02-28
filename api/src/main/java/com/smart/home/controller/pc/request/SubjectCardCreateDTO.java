package com.smart.home.controller.pc.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Data
@ToString
public class SubjectCardCreateDTO {

    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("插入位置")
    private String subTitle;
    @ApiModelProperty("插入链接")
    private String url;

}
