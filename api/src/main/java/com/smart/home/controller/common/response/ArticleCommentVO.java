package com.smart.home.controller.common.response;

import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.UserBaseInfo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Data
@ToString
public class ArticleCommentVO extends UserBaseInfo {

    @ApiModelProperty("文章评论主键id")
    private Long id;
    @ApiModelProperty("文章主键id")
    private Long articleId;
    @ApiModelProperty("文章作者主键id")
    private Long toUserId;
    @ApiModelProperty("评论内容")
    private String contents;
    @ApiModelProperty("评价图片数组")
    private List<String> imageList;
    @ApiModelProperty("评论时间")
    private Date createdTime;
    @ApiModelProperty("点赞数量")
    private Integer likeCount;
    @ApiModelProperty("点踩数量")
    private Integer stampCount;
    @ApiModelProperty("是否是作者-0否1是")
    private Integer authorFlag;
    @ApiModelProperty("我有没有点过赞了-0否1是")
    private Integer likeFlag;
    @ApiModelProperty("我有没有点过踩了-0否1是")
    private Integer stampFlag;
    @ApiModelProperty("回复列表-分页")
    private ResponsePageBean<ArticleCommentReplyVO> replyResultList;

}
