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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jason
 * @date 2021/3/8
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = EsConstant.productCommentIndex, type = EsConstant.productComment, shards = 3, replicas = 1)
public class ProductCommentBean extends PersionalMessageDTO {

    @Id
    @Field(type = FieldType.Long, index = true)
    private Long id;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Integer)
    private Integer productId;

    @Field(type = FieldType.Float, index = false)
    private BigDecimal starCount;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String details;

    @Field(type = FieldType.Keyword, index = false)
    private String images;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    //标签，标记为文章，帖子，评论等 见EsSaveTypeEnum
    @Field(type = FieldType.Integer, index = true)
    private Integer saveType;
}
