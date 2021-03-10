package com.smart.home.controller.app.response.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/9
 **/
@Data
@ToString
public class MyRootProfileProductCommentVO {

    @ApiModelProperty("评价主键id")
    private Long id;
    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户等级")
    private Integer userLevel;
    @ApiModelProperty("星等数量")
    private BigDecimal starCount;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("内容")
    private String details;
    @ApiModelProperty("图片数组")
    private List<String> imageList;
    @ApiModelProperty("有趣数量")
    private Integer funCount;
    @ApiModelProperty("赞数量")
    private Integer likeCount;
    @ApiModelProperty("踩数量")
    private Integer stampCount;
    @ApiModelProperty("回复数量")
    private Integer replyCount;
    @ApiModelProperty("我有没有点有趣过：0否1是")
    private Integer funFlag;
    @ApiModelProperty("我有没有点赞过：0否1是")
    private Integer likeFlag;
    @ApiModelProperty("我有没有点踩过：0否1是")
    private Integer stampFlag;

}
