package com.smart.home.es.service;

import com.smart.home.common.util.DateUtils;
import com.smart.home.common.util.UUIDUtil;
import com.smart.home.es.dto.NameCountDTO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/18
 * 聚合查询服务
 **/
@Service
public class EsAggregationQueryService extends EsQueryService {

    /**
     * 单字段聚合
     * @param fieldName 需要聚合计算的字段
     * @param fetchSize 最终计算出多少条数据
     * @param T
     * @param <T>
     * @return
     */
    public <T> List<NameCountDTO> queryForList(String fieldName, int fetchSize, Class<T> T) throws Exception {
        // 查询最近一个月的
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = DateUtils.getCurrentMonthFirstDay();
        String endDateString = DateUtils.getCurrentMonthLastDay();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("createTime")
                .from(DateUtils.getDateTimeString(df.parse(startDateString)))
                .to(DateUtils.getDateTimeString(df.parse(endDateString)))
        );
        // 定义一个聚合名称
        String aggregationName = UUIDUtil.uuid();
        // 创建聚合查询条件
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders
                .terms(aggregationName)
                .field(fieldName)
                // true为升序 false为降序 同BucketOrder.aggregation
                .order(BucketOrder.count(false))
                .size(fetchSize);
        // 创建查询对象
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                //添加查询条件
                .withQuery(boolQueryBuilder)
                .addAggregation(termsAggregationBuilder)
                //符合查询条件的文档分页（不是聚合的分页）
                .withPageable(PageRequest.of(0, 1))
                .build();
        // 执行查询
        AggregatedPage<T> pageList = elasticsearchTemplate.queryForPage(searchQuery, T);
        // 取出聚合结果
        Aggregations entitiesAggregations = pageList.getAggregations();
        Terms terms = (Terms) entitiesAggregations.asMap().get(aggregationName);
        // 遍历取出聚合字段列的值，与对应的数量
        List<NameCountDTO> list = new ArrayList<>();
        NameCountDTO nameCountDTO = null;
        for (Terms.Bucket bucket : terms.getBuckets()) {
            // 聚合字段列的值
            String keyAsString = bucket.getKeyAsString();
            // 聚合字段对应的数量
            long docCount = bucket.getDocCount();
            nameCountDTO = new NameCountDTO();
            nameCountDTO.setName(keyAsString);
            nameCountDTO.setCount(docCount);
            list.add(nameCountDTO);
        }
        return list;
    }

    /**
     * 字段嵌套聚合 TODO DEMO
     */
    public <T> void queryForList(String filedName1, String filedName2, int fetchSize, Class<T> T) {
        // 定义聚合名称
        String aggregationName1 = UUIDUtil.uuid();
        String aggregationName2 = UUIDUtil.uuid();
        // 创建聚合查询条件
        TermsAggregationBuilder agg2 = AggregationBuilders
                .terms(aggregationName2)
                .field(filedName2)
                .size(fetchSize);
        TermsAggregationBuilder agg1 = AggregationBuilders
                .terms(aggregationName1)
                .field(filedName1)
                .size(fetchSize)
                .subAggregation(agg2);
        // 创建查询对象
        SearchQuery build = new NativeSearchQueryBuilder()
                .addAggregation(agg1)
                .withPageable(PageRequest.of(0, 1))
                .build();
        // 执行查询
        AggregatedPage<T> pageList = elasticsearchTemplate.queryForPage(build, T);
        // 取出聚合结果
        Aggregations entitiesAggregations = pageList.getAggregations();
        Terms terms = (Terms) pageList.getAggregation(aggregationName1);
        // 遍历最外层的聚合结果
        for (Terms.Bucket bucket1 : terms.getBuckets()) {
            // 聚合字段列的名称
            String keyAsString = bucket1.getKeyAsString();
            // 聚合字段对应的数量
            long docCount = bucket1.getDocCount();
            // 获取内部的聚合字段信息
            Aggregations aggregations = bucket1.getAggregations();
            Terms terms2 = aggregations.get(aggregationName2);
            // 遍历获取agg2的聚合信息
            for (Terms.Bucket bucket2 : terms2.getBuckets()) {
                String keyAsString2 = bucket2.getKeyAsString();
                long docCount2 = bucket2.getDocCount();
            }
        }
    }

    /**
     * 多字段一起聚合 TODO DEMO
     */
    public <T> void queryForList(List<String> fieldNameList, int fetchSize, Class<T> T) {
        // 定义聚合名称
        String aggregationName = UUIDUtil.uuid();
        // 创建聚合查询条件
        // 编写script语句
        String scriptString = "";
        int index = 1;
        int total = fieldNameList.size();
        for (String fieldName : fieldNameList) {
            if (index == total) {
                scriptString += "doc['"+fieldName+"'].values";
            } else {
                scriptString += "doc['"+fieldName+"'].values +'/'+ ";
            }
            index ++;
        }
        Script script = new Script(scriptString);
        // 创建一个聚合查询对象
        TermsAggregationBuilder agg = AggregationBuilders.terms(aggregationName).script(script).size(fetchSize);
        // 创建查询对象
        SearchQuery build = new NativeSearchQueryBuilder()
                .addAggregation(agg)
                .withPageable(PageRequest.of(0, 1))
                .build();
        // 执行查询
        AggregatedPage<T> pageList = elasticsearchTemplate.queryForPage(build, T);
        // 取出聚合结果
        Terms terms = (Terms) pageList.getAggregation(aggregationName);
        // 遍历最外层的聚合结果
        for (Terms.Bucket bucket : terms.getBuckets()) {
            // 循环取出聚合的值
            String keyAsString = bucket.getKeyAsString();
            // 切割取出每个字段
            String[] split = keyAsString.split("/");
        }
    }
}
