package com.smart.home.es.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
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
@Accessors(chain = true)
@Document(indexName = "smart_search_key")
public class SearchKey {

    @Id
    @Field(type = FieldType.Keyword, name = "id")
    private String id;

    @Field(type = FieldType.Keyword, name = "keyword")
    private String keyword;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Date,
            format = DateFormat.custom,
            pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public SearchKey(String id, String keyword, String content, Date createTime) {
        this.id = id;
        this.keyword = keyword;
        this.content = content;
        this.createTime = createTime;
    }

}
