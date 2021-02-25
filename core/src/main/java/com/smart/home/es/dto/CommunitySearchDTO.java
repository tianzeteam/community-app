package com.smart.home.es.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Created by tangchenglong on 2021/2/25.
 */
@Data
@NoArgsConstructor
@Builder
@ToString
@Accessors(chain = true)
public class CommunitySearchDTO implements java.io.Serializable
{
    private String keyword;

    private Long categoryId;

    private Map<String,String> sorts;
}
