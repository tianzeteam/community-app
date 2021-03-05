package com.smart.home.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/3/5
 **/
@Data
@ToString
public class ContentAuditAdminRecordTO {

    private Long id;
    private String nickName;
    private String title;
    private Integer contentType;
    private String contents;
    private String images;
    private Integer hitSensitiveCount;
    private Integer reportCount;
    private String reportCountRate;
    private Integer autoAuditFlag;
    private String textExceptionReason;
    private String imageExceptionReason;
    private Date createdTime;

    private Long userId;
    private Integer auditFlag;
    private Integer likeCount;
    private Integer replyCount;

}
