package com.smart.home.controller.pc.response.article;

import com.smart.home.controller.pc.request.article.ArticleUpdateVideoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class ArticleEditVideoVO extends ArticleUpdateVideoDTO {

    @ApiModelProperty("未过审原因")
    private String rejectReason;
    @ApiModelProperty("审核状态：0待审核1审核通过2审核不通过")
    private Integer auditState;

}
