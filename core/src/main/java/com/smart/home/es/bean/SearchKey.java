package com.smart.home.es.bean;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author jason
 * @date 2021/2/17
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Accessors(chain = true)
@Document(indexName = "smart_search_key", shards = 1, replicas = 0)
public class SearchKey {

    @Id
    private String id;

    @Field(type = FieldType.Keyword, name = "keyword")
    private String keyword;

    @Field(type = FieldType.Date,
            format = DateFormat.custom,
            pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
