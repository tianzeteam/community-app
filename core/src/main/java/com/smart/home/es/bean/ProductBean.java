package com.smart.home.es.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.home.es.dto.KeyValueDTO;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 * @date 2021/3/7
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Accessors(chain = true)
@Document(indexName = "product_index", type = "product", shards = 3, replicas = 1)
public class ProductBean {

    @Id
    private Integer id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String productName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String remark;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String details;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String tag;

    @Field(type = FieldType.Nested)
    private List<KeyValueDTO> keyValueDTOList;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd", index = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date onlineDate;

    @Field(type = FieldType.Integer, index = false)
    private Integer onlineFlag;

    @Field(type = FieldType.Integer, index = false)
    private Integer recommendFlag;

    @Field(type = FieldType.Keyword)
    private String brandName;

    @Field(type = FieldType.Integer, index = false)
    private Integer categoryOneId;
    @Field(type = FieldType.Integer, index = false)
    private Integer categoryTwoId;
    @Field(type = FieldType.Integer, index = false)
    private Integer categoryThreeId;

    @Field(type = FieldType.Keyword)
    private Integer categoryOneName;
    @Field(type = FieldType.Keyword)
    private Integer categoryTwoName;
    @Field(type = FieldType.Keyword)
    private Integer categoryThreeName;

    @Field(type = FieldType.Keyword)
    private String supportPlatform;

    @Field(type = FieldType.Double, index = false)
    private BigDecimal averageScore;
    @Field(type = FieldType.Integer, index = false)
    private Integer praiseRate;
    @Field(type = FieldType.Double, index = false)
    private BigDecimal hotRate;

    @Field(type = FieldType.Keyword, index = false)
    private String coverImage;

    @Field(type = FieldType.Integer, index = false)
    private Integer commentCount;

    @Field(type = FieldType.Integer, index = false)
    private Integer testCount;

    @Field(type = FieldType.Integer, index = false)
    private Integer collectCount;

    @Field(type = FieldType.Integer, index = false)
    private Integer deleteFlag;

}
