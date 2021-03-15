package com.smart.home.es.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsSearchDTO {

    //更新查询时可以用
    private Long id;

    private Long userId;

    private String contents;

}
