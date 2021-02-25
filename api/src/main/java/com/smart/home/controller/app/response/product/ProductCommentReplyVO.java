package com.smart.home.controller.app.response.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class ProductCommentReplyVO {

    @ApiModelProperty("回复主键id")
    private Long id;
    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户等级")
    private Integer userLevel;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("回复内容")
    private String details;
    @ApiModelProperty("赞数量")
    private Integer likeCount;
    @ApiModelProperty("踩数量")
    private Integer stampCount;

    @ApiModelProperty("点过赞0否1是-登陆后才有值")
    private Integer likeFlag;
    @ApiModelProperty("点过踩0否1是-登陆后才有值")
    private Integer stampFlag;

}
