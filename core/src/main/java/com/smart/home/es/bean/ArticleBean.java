package com.smart.home.es.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.home.dto.PersionalMessageDTO;
import com.smart.home.es.common.EsConstant;
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
 * @date 2021/3/7
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = EsConstant.articleIndex, type = EsConstant.article, shards = 3, replicas = 1)
public class ArticleBean extends PersionalMessageDTO {

    @Id
    @Field(type = FieldType.Long, index = true)
    private Long id;

    @Field(type = FieldType.Long, index = true)
    private Long userId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String remark;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String details;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss", index = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    @Field(type = FieldType.Integer, index = false)
    private Integer category;

    @Field(type = FieldType.Integer, index = false)
    private Integer recommendFlag;

    @Field(type = FieldType.Keyword, index = false)
    private String channelName;

    @Field(type = FieldType.Keyword, index = false)
    private String coverImage;

    @Field(type = FieldType.Integer, index = false)
    private Integer commentCount;

    @Field(type = FieldType.Integer, index = false)
    private Integer likeCount;

    @Field(type = FieldType.Integer, index = false)
    private Integer stampCount;

    @Field(type = FieldType.Integer, index = false)
    private Integer reportCount;

    @Field(type = FieldType.Integer, index = false)
    private Integer visitCount;

    @Field(type = FieldType.Integer, index = false)
    private Integer collectCount;

    @Field(type = FieldType.Integer, index = true)
    private Integer articleType;

    //标签，标记为文章，帖子，评论等 见EsSaveTypeEnum
    @Field(type = FieldType.Integer, index = true)
    private Integer saveType;

}
