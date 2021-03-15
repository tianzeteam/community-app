package com.smart.home.es.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductEsDTO extends EsSearchDTO{

    private String productName;

}
