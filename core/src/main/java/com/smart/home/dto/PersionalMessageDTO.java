package com.smart.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersionalMessageDTO {

    private String headImg;

    private String nickname;

    private Integer level;

    private String userRemark;

}
