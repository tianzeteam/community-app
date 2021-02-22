package com.smart.home.es.dto;

import lombok.*;

/**
 * @author jason
 * @date 2021/2/18
 **/
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NameCountDTO {

    private String name;
    private Long count;

}
