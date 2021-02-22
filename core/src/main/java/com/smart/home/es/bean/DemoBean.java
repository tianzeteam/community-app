package com.smart.home.es.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

/**
 * @author jason
 * @date 2021/2/18
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "paper_base", type = "doc")
//@Setting(settingPath = "/elasticsearch/settings.json")//可设置主分片、副本分片、设置默认停用词等
public class DemoBean {

    @Id
    @Field(type = FieldType.Keyword, name = "paperBaseId")
    private String paperBaseId;

    /**
    * 试卷名称
    */
    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "standard" , name = "paperBaseName"),
                 otherFields = {
                         @InnerField(suffix = "zh", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
                         @InnerField(suffix = "en", type = FieldType.Text, analyzer = "english"),
                 })
    private String paperBaseName;

    /**
    * 共享级别名,可以使用分词器查询，模糊匹配
    */
    @Field(type = FieldType.Text, name = "shareLevelName")
    private String shareLevelName;

    /**
    * 创建人，不可使用分词器查询，精准匹配
    */
    @Field(type = FieldType.Keyword, name = "personId")
    private String personId;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, name = "createtime", format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private String createtime;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date, name = "lastUpdateTime", format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private String lastUpdateTime;

    /**
    * 删除标识    0:未删除，1:已删除
    */
    @Field(type = FieldType.Keyword, name = "deleteFlag")
    private String deleteFlag;

}
