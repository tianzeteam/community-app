package com.smart.home.controller.pc.response.article;

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
public class ArticleAdminRecommendPageVO extends ArticleAdminReviewPageVO {

    @ApiModelProperty("推荐时间")
    private Date recommendTime;
    @ApiModelProperty("推荐类型：0文章卡片1大图卡片2置顶大图")
    private Integer recommendType;

}
