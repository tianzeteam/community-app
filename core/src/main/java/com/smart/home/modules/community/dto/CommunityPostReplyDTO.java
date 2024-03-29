package com.smart.home.modules.community.dto;

import lombok.Data;

@Data
public class CommunityPostReplyDTO {

    private Long id;

    private Long userId;

    private Long authorUserId;

    private Long postId;

    private Long postReplyId;

    //0主贴回复1二级回复
    private Integer replyType;

    //热门
    private Integer hotSortFlag;

    //正序1，倒序0
    private Integer sortFlag;


}
