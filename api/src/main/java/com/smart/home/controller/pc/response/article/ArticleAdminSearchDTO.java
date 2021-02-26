package com.smart.home.controller.pc.response.article;

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
public class ArticleAdminSearchDTO extends RequestPageBean {

    @ApiModelProperty("排序规则：desc 或者 asc")
    private String sortType;
    @ApiModelProperty("排序字段:created_time")
    private String sortField;
    @ApiModelProperty("审核状态：0待审核1审核通过2审核未通过")
    private Integer auditState;
    @ApiModelProperty("是否是推荐文章：0否1是")
    private Integer recommendFlag;

}
