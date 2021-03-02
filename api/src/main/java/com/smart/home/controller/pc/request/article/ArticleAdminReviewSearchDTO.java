package com.smart.home.controller.pc.request.article;

import com.smart.home.dto.RequestPageBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class ArticleAdminReviewSearchDTO extends RequestPageBean {

    @ApiModelProperty("排序规则：desc 或者 asc")
    private String sortType;
    @ApiModelProperty("排序字段:created_time,audit_time")
    private String sortField;

}
