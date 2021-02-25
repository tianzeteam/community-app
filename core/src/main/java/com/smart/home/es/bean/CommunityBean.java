package com.smart.home.es.bean;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@ToString
@Accessors(chain = true)
@Document(indexName = "community", type = "_doc", shards = 1, replicas = 0)
public class CommunityBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    @Field(type = FieldType.Long)
    private Long categoryId;
    @Field(type = FieldType.Keyword)
    private String categoryName;
    @Field(type = FieldType.Keyword, index = false)
    private String coverImage;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;
    @Field(type = FieldType.Text)
    private String remark;
    @Field(type = FieldType.Integer)
    private Integer followerCount;
    @Field(type = FieldType.Integer)
    private Integer sort;
    @Field(type = FieldType.Integer)
    private Integer topFlag;
    @Field(type = FieldType.Integer)
    private Integer boutiqueFlag;
    @Field(type = FieldType.Long)
    private Long createUserId;
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

}