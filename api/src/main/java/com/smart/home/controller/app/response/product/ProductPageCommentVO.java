package com.smart.home.controller.app.response.product;

import com.smart.home.dto.UserBaseInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/25
 **/
@ApiModel("产品页面的评价列表")
@Data
@ToString
public class ProductPageCommentVO extends UserBaseInfo {

    @ApiModelProperty("评价星等")
    private BigDecimal starCount;
    @ApiModelProperty("评价时间")
    private Date createdTime;
    @ApiModelProperty("评价内容")
    private String details;
    @ApiModelProperty("图片数组")
    private List<String> imageList;

    @ApiModelProperty("有趣数量")
    private Integer funCount;
    @ApiModelProperty("喜欢数量")
    private Integer likeCount;
    @ApiModelProperty("踩数量")
    private Integer stampCount;
    @ApiModelProperty("回复数量")
    private Integer replyCount;
    @ApiModelProperty("我有没有点有趣：0否1是")
    private Integer funFlag;
    @ApiModelProperty("我有没有点喜欢：0否1是")
    private Integer likeFlag;
    @ApiModelProperty("我有没有点踩：0否1是")
    private Integer stampFlag;

}
