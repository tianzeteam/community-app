package com.smart.home.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author jason
 * @date 2021/3/5
 **/
@Data
@ToString
public class ContentAdminAuditSearchTO {

    private List<Integer> contentTypeList;
    private Long userId;
    private Integer contentException;
    private Integer hitSensitiveCount;
    private Integer reportCount;
    private Integer textExceptionFlag;
    private Integer imageExceptionFlag;
    private String sortField;
    private Integer pageSize;

}
