package com.smart.home.controller.pc.response.user;

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
public class PcUserProfileArticleVO {

    @ApiModelProperty("文章主键id")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("发布时间")
    private Date createdTime;
    @ApiModelProperty("审核状态：0待审核1审核通过2审核失败")
    private Integer auditState;
    @ApiModelProperty("0草稿1上线")
    private Integer state;
    @ApiModelProperty("投稿类型：0文章1视频")
    private Integer category;

}
