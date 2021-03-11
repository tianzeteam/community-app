package com.smart.home.controller.app.response.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ApiModel("推荐帖子")
@Data
public class RecommendCommunityPostVO {

    @ApiModelProperty("帖子主键id")
    private Long id;
    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户等级")
    private Integer userLevel;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("赞数量")
    private Integer likeCount;
    @ApiModelProperty("回复数量")
    private Integer replyCount;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("正文")
    private String contents;
    @ApiModelProperty("图片数组")
    private List<String> imageList;

}
