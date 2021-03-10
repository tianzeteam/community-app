package com.smart.home.controller.app.response.community;

import com.smart.home.dto.UserBaseInfo;
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
@ApiModel("我的收藏-投稿")
@Data
@ToString
public class MyCollectArticleVO extends UserBaseInfo {

    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("赞数量")
    private Integer likeCount;
    @ApiModelProperty("评论数量")
    private Integer commentCount;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("正文")
    private String details;
    @ApiModelProperty("轮播图")
    private List<String> imageList;
    @ApiModelProperty("标签")
    private String tag;

}
