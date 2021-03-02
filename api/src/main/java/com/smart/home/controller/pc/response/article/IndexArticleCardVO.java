package com.smart.home.controller.pc.response.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/3/2
 **/
@Data
@ToString
public class IndexArticleCardVO {

    @ApiModelProperty("文章主键id")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("来源")
    private String sourceFrom;
    @ApiModelProperty("频道名称")
    private String channelName;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("创建时间")
    private Date createdTime;

}
