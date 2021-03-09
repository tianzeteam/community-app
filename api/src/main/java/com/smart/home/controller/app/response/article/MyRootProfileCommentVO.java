package com.smart.home.controller.app.response.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/3/9
 **/
@Data
@ToString
public class MyRootProfileCommentVO {

    @ApiModelProperty("评论主键id")
    private Long id;
    @ApiModelProperty("用户头像地址")
    private String headUrl;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户等级")
    private Integer userLevel;
    @ApiModelProperty("是否是作者：0否1是")
    private Integer authorFlag;
    @ApiModelProperty("评论内容")
    private String contents;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("赞数量")
    private Integer likeCount;
    @ApiModelProperty("踩数量")
    private Integer stampCount;
    @ApiModelProperty("我有没有赞过：0否1是")
    private Integer likeFlag;
    @ApiModelProperty("我有没有踩过：0否1是")
    private Integer stampFlag;

}
