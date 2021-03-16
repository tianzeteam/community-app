package com.smart.home.controller.common.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/27
 **/
@Data
@ToString
public class ArticleDetailVO {

    @ApiModelProperty("文章主键id")
    private Long id;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("轮播图片")
    private List<String> imageList;
    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("用户主键id")
    private Long userId;
    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户等级")
    private Integer userLevel;
    @ApiModelProperty("用户签名")
    private String sign;
    @ApiModelProperty("我是否关注了该用户:0否1是")
    private Integer focusUserFlag;
    @ApiModelProperty("浏览量")
    private Integer visitCount;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("我是是否关注了：0否1是")
    private Integer focusFlag;
    @ApiModelProperty("频道吗？ 全屋方案")
    private String channelName;
    @ApiModelProperty("正文")
    private String details;
    @ApiModelProperty("获赞数量")
    private Integer likeCount;
    @ApiModelProperty("评论数量")
    private Integer commentCount;
    @ApiModelProperty("我有没有赞过了：0否1是")
    private Integer likeFlag;
    @ApiModelProperty("我有没有踩过了：0否1是")
    private Integer stampFlag;
    @ApiModelProperty("我有没有收藏过了：0否1是")
    private Integer collectFlag;

    @ApiModelProperty("是否原创:0原创1转载")
    private Integer articleType;
    @ApiModelProperty("转载来源")
    private String originalUrl;
    @ApiModelProperty("原作者")
    private String sourceFrom;
    @ApiModelProperty("转载是否获得授权：0未授权1已获得授权")
    private Integer originalAuth;
    @ApiModelProperty("评测结果")
    private ProductTestResultVO productTestResultVO;

}
