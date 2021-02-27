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
public class ArticleAdminReviewPageVO extends ArticleAdminPageVO {

    @ApiModelProperty("是否推荐：0否1是")
    private Integer recommendFlag;
    @ApiModelProperty("上线状态:0正常1撤稿")
    private Integer onlineStatus;
    @ApiModelProperty("过审时间")
    private Date auditTime;

}
