package com.smart.home.controller.pc.request.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class ArticleCreateDTO {

    @ApiModelProperty("标题")
    @NotBlank(message = "标题不能为空")
    @Size(max = 40)
    private String title;

    @ApiModelProperty("正文")
    @NotBlank(message = "正文不能为空")
    private String details;

    @ApiModelProperty("状态：0保存草稿1投稿")
    @NotNull(message = "状态不能为空")
    private Integer state;

    @ApiModelProperty("封面图片")
    private String coverImage;

    @ApiModelProperty("轮播图片列表")
    private List<String> bannerImagesList;

    @ApiModelProperty("下拉选择的频道主键id")
    @NotNull(message = "请选择频道")
    private Long channelId;

    @ApiModelProperty("文章类型：0原创1转载")
    private Integer articleType;

    @ApiModelProperty("原创版权申明")
    private String copyrightNotice;

    @ApiModelProperty("转载来源")
    private String originalUrl;

    @ApiModelProperty("转载授权0未授权1已授权")
    private Integer originalAuth;

    @ApiModelProperty("原作者或者媒体名称")
    private String sourceFrom;

    @ApiModelProperty("插入的产品主键id")
    private Integer productId;

    @ApiModelProperty("评测结论：评测是针对产品的，所以插入产品后才能插入评测")
    private ProductTestResultDTO productTestResultDTO;
}
