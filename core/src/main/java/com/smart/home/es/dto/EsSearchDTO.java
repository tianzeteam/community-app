package com.smart.home.es.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class EsSearchDTO {

    //更新查询时可以用
    private Long id;

    private Long userId;

    private String contents;

}
