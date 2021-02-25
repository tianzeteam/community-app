package com.smart.home.es.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Created by tangchenglong on 2021/2/25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Accessors(chain = true)
public class CommunitySearchDTO implements java.io.Serializable
{
    private String keyword;

    private Long categoryId;

    private Map<String,String> sorts;
}
