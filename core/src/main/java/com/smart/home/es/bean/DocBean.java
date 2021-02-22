package com.smart.home.es.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author jason
 * @date 2021/2/17
 * indexName 相当于数据库
 * type 相当于数据库表
 * shards 分片数量默认数量为5个，一旦创建不可更改数量
 * replicas shards的副本，用于冗余数据及提高搜索性能
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "smart", type = "_doc", shards = 1, replicas = 0)
public class DocBean {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String firstCode;

    @Field(type = FieldType.Keyword)
    private String secordCode;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Integer)
    private Integer type;

    public DocBean(Long id, String firstCode, String secordCode, String content, Integer type) {
        this.id = id;
        this.firstCode = firstCode;
        this.secordCode = secordCode;
        this.content = content;
        this.type = type;
    }
}
