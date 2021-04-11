package com.smart.home.controller.app.response.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/9
 **/
@Data
@ToString
public class MyRootProfileArticleVO {

    @ApiModelProperty("文章主键id")
    private Long id;
    @ApiModelProperty("文章分类：0文章1视频")
    private Integer category;
    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户等级")
    private Integer userLevel;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容")
    private String details;
    private List<String> imageList;
    @ApiModelProperty("赞数量")
    private Integer likeCount;
    @ApiModelProperty("踩数量")
    private Integer stampCount;
    @ApiModelProperty("评论数量")
    private Integer commentCount;
    @ApiModelProperty("我有没有点赞过：0否1是")
    private Integer likeFlag;
    @ApiModelProperty("我有没有点踩过：0否1是")
    private Integer stampFlag;

}
