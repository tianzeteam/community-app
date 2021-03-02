package com.smart.home.controller.pc.request.article;

import com.smart.home.dto.IdListBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Data
@ToString
public class ArticleAdminRejectDTO extends IdListBean {

    @ApiModelProperty("拒绝过审原因")
    private String rejectReason;

}
