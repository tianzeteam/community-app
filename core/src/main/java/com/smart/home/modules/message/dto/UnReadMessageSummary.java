package com.smart.home.modules.message.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/4/21
 **/
@Data
@ToString
public class UnReadMessageSummary {

    private Long senderId;
    private Long receiverId;
    private Long totalCount;
    private String nickName;
    private String headUrl;
    private String latestMessage;

}
