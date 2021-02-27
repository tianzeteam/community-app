package com.smart.home.controller.app.request.artcile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Data
@ToString
public class ArticleRecommendOnDTO {

    @ApiModelProperty("文章主键id")
    private Long articleId;
    @ApiModelProperty("推荐类型：0文章卡片1大图卡片2置顶大图")
    private Integer recommendType;

}
