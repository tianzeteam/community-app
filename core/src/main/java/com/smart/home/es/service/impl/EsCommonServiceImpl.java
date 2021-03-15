package com.smart.home.es.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.smart.home.es.dto.EsSearchDTO;
import com.smart.home.es.dto.ProductEsDTO;
import com.smart.home.es.service.EsCommonService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class EsCommonServiceImpl<T> implements EsCommonService<T> {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Override
    public void insertOrUpdateOne(String idxName, T t) {
        IndexRequest request = new IndexRequest(idxName);
        log.info("es插入数据:index:{}  :  {}", idxName, JSON.toJSONString(t));
        request.source(t, XContentType.JSON);
        try {
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public <T> List<T> searchMultiple(String[] idxNames, EsSearchDTO esSearchDTO, Class<T> c) {
        if (esSearchDTO.getUserId() == null) {
            log.error("参数为空");
            return Collections.EMPTY_LIST;
        }
        SearchRequest searchRequest = new SearchRequest(idxNames);;
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("userId", esSearchDTO.getUserId().toString());
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(queryBuilder);
        sourceBuilder.query(boolQueryBuilder);//多条件查询
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.size(10000);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<T> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), c));
            }
            return res;
        } catch (IOException e) {
            log.error("es 查询异常：{}", e.toString());
            log.error("" + e);
        }
        return Collections.EMPTY_LIST;
    }

    public void productUpdate(String indexName, ProductEsDTO productEsDTO){
        if (productEsDTO.getUserId() == null) {
            log.error("userid is null");
            return;
        }
        UpdateByQueryRequest request = new UpdateByQueryRequest(indexName);
        // 更新时版本冲突
        request.setConflicts("proceed");
        //request.setSize(10);
        //request.setBatchSize(100);
        request.setRefresh(true);


        //多字段更新 TODO
        if (StrUtil.isNotEmpty(productEsDTO.getProductName())) {
            request.setScript(new Script(productEsDTO.getProductName()));
        }



        BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
        mustQuery.must(QueryBuilders.termQuery("id", productEsDTO.getId().toString()));
        request.setQuery(mustQuery);
        try {
            //同步
            restHighLevelClient.updateByQuery(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es update error：{}", e);
            e.printStackTrace();
        }
    }


}
