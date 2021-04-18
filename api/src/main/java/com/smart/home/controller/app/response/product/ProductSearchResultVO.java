package com.smart.home.controller.app.response.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class ProductSearchResultVO {

    @ApiModelProperty("产品主键id")
    private Integer id;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品简介")
    private String remark;
    @ApiModelProperty("产品标签")
    private List<String> tagList;
    @ApiModelProperty("评论数量")
    private Integer commentCount;
    @ApiModelProperty("综合评分")
    private BigDecimal averageScore;
    @ApiModelProperty("好评率, 不带%号的，前端自己加%")
    private BigDecimal praiseRate;
    @ApiModelProperty("支持的平台")
    private String supportPlatform;

    public BigDecimal getPraiseRate() {
        if (Objects.isNull(praiseRate)) {
            return new BigDecimal(100);
        }
        return praiseRate;
    }

    public BigDecimal getAverageScore() {
        if (Objects.isNull(averageScore)) {
            return new BigDecimal(0);
        }
        return averageScore;
    }

}
