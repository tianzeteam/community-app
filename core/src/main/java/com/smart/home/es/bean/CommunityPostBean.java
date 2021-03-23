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
 * 帖子bean
 * 不存contents
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = EsConstant.communityPostIndex, type = EsConstant.communityPost, shards = 3, replicas = 1)
public class CommunityPostBean  extends PersionalMessageDTO {

    @Field(type = FieldType.Long, index = true)
    private Long id;

    @Field(type = FieldType.Integer, index = true)
    private Integer community;

    @Field(type = FieldType.Long, index = true)
    private Long userId;

    @Field(type = FieldType.Text, index = false)
    private String images;

    @Field(type = FieldType.Integer, index = false)
    private Integer topFlag;

    @Field(type = FieldType.Integer, index = false)
    private Integer boutiqueFlag;

    @Field(type = FieldType.Integer, index = false)
    private Integer commentFlag;

    @Field(type = FieldType.Integer, index = false)
    private Integer auditStatus;

    @Field(type = FieldType.Integer, index = true)
    private Integer state;
    /**
     * 回复数量
     */
    @Field(type = FieldType.Integer, index = false)
    private Integer replyCount;
    @Field(type = FieldType.Integer, index = false)
    private Integer autoAuditFlag;
    @Field(type = FieldType.Integer, index = false)
    private Integer hitSensitiveCount;
    /**
     * 分享数量
     */
    @Field(type = FieldType.Integer, index = false)
    private Integer shareCount;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String remark;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String contents;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss", index = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

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


    //标签，标记为文章，帖子，评论等 见EsSaveTypeEnum
    @Field(type = FieldType.Integer, index = false)
    private Integer saveType;


}
