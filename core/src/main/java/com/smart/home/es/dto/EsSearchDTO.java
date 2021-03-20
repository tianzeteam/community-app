package com.smart.home.es.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsSearchDTO {

    //更新查询时可以用
    private Long id;

    private Long userId;

    private String contents;

    private Integer saveType;

    //
    private List<Long> longList;

    private Integer from = 0;

    private Integer size = 1000;

}
