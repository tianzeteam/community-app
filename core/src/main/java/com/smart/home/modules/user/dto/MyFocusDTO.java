package com.smart.home.modules.user.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class MyFocusDTO {

    private String headUrl;
    private String nickName;
    private String sign;
    private Long focusUserId;

}
