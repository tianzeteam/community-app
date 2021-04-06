package com.smart.home.controller.pc.response.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class ArticleAdminPageVO {

    @ApiModelProperty("作者用户主键id")
    private Long authorId;
    @ApiModelProperty("文章主键id")
    private Long articleId;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("投稿类型：0文章1视频")
    private Integer category;
    @ApiModelProperty("投稿频道")
    private String channelName;
    @ApiModelProperty("投稿时间")
    private Date createdTime;

}
