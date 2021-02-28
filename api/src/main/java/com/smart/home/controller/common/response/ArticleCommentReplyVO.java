package com.smart.home.controller.common.response;

import com.smart.home.dto.UserBaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Data
@ToString
public class ArticleCommentReplyVO extends UserBaseInfo {

    @ApiModelProperty("回复主键id")
    private Long id;
    @ApiModelProperty("文章主键id")
    private Long articleId;
    @ApiModelProperty("一级评论主键id")
    private Long articleCommentId;
    @ApiModelProperty("回复时间")
    private Date createdTime;
    @ApiModelProperty("回复内容")
    private String contents;

}
