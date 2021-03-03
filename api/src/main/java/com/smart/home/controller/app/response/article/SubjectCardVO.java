package com.smart.home.controller.app.response.article;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/3
 **/
@Data
@ToString
public class SubjectCardVO {

    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("标题")
    private String coverImage;
    @ApiModelProperty("标题")
    private String url;

}
