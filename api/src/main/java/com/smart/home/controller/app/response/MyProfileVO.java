package com.smart.home.controller.app.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/24
 **/
@Data
@ToString
public class MyProfileVO {

    @ApiModelProperty("头像地址")
    private String headUrl;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("等级")
    private Integer userLevel;
    @ApiModelProperty("标签")
    private String tag;
    @ApiModelProperty("签名")
    private String sign;
    @ApiModelProperty("关注数量")
    private Integer focusCount;
    @ApiModelProperty("粉丝数量")
    private Integer followCount;
    @ApiModelProperty("获赞数量")
    private Integer likeCount;


///////////////////////////////////////////////
    @ApiModelProperty("发帖数量")
    private Integer postCount;
    @ApiModelProperty("投稿评论数量")
    private Integer commentCount;
    @ApiModelProperty("社区回帖数量")
    private Integer replyCount;
    @ApiModelProperty("产品评价数量")
    private Integer evaluateCount;
    @ApiModelProperty("文章投稿数量")
    private Integer contributeCount;
/////////////////////////////////////////////
    @ApiModelProperty("粉丝在个人主页可以看到的勾选的动态:0发帖1评论2回帖3评价4投稿")
    private List<Integer> userPrivatePrivacyList;
    @ApiModelProperty("我是否关注了该用户:0否1是")
    private Integer focusFlag;

}
