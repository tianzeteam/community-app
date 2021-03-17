package com.smart.home.es.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.smart.home.es.common.EsConstant;
import com.smart.home.es.dto.CommunityPostEsDTO;
import com.smart.home.es.dto.EsSearchDTO;
import com.smart.home.es.dto.ProductEsDTO;
import com.smart.home.es.service.EsCommonService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
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
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Struct;
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
    public void insertOrUpdateOne(String idxName, String type, Long id, T t) {
        IndexRequest request = new IndexRequest(idxName, type, id.toString());
        log.info("es插入数据:index:{}  :  {}", idxName, JSON.toJSONString(t));
        request.source(JSON.toJSONString(t), XContentType.JSON);
        request.id(id.toString());
        try {
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<String> searchMultiple(String[] idxNames, EsSearchDTO esSearchDTO) {
        SearchRequest searchRequest = new SearchRequest(idxNames);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (esSearchDTO.getUserId() != null) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("userId", esSearchDTO.getUserId().toString());
            boolQueryBuilder.must(queryBuilder);
        }
        if (CollUtil.isNotEmpty(esSearchDTO.getLongList())) {
            QueryBuilder queryBuilder = QueryBuilders.termsQuery("userId", esSearchDTO.getLongList());
            boolQueryBuilder.must(queryBuilder);
        }
        sourceBuilder.query(boolQueryBuilder);//多条件查询
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.size(10000);
        sourceBuilder.sort("id", SortOrder.DESC);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(hit.getSourceAsString());
            }
            return res;
        } catch (IOException e) {
            log.error("es 查询异常：{}", e.toString());
            log.error("" + e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<T> search(String indexName, EsSearchDTO esSearchDTO, Class<T> c) {
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = null;
        if (StrUtil.equalsIgnoreCase(indexName, EsConstant.communityPostIndex)) {
            boolQueryBuilder = buildCommunityPostCondition(sourceBuilder, esSearchDTO);
        } else if (StrUtil.equalsIgnoreCase(indexName, EsConstant.articleIndex)) {
            boolQueryBuilder = buildArticleCondition(sourceBuilder, esSearchDTO);
        } else if (StrUtil.equalsIgnoreCase(indexName, EsConstant.productIndex)) {
            boolQueryBuilder = buildProductCondition(sourceBuilder, esSearchDTO);
        } else if (StrUtil.equalsIgnoreCase(indexName, EsConstant.productCommentIndex)) {
            boolQueryBuilder = buildProductCommentCondition(sourceBuilder, esSearchDTO);
        }


        sourceBuilder.query(boolQueryBuilder);//多条件查询
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.from(esSearchDTO.getFrom() <= 0 ? 0 : esSearchDTO.getFrom() * esSearchDTO.getSize());
        sourceBuilder.size(esSearchDTO.getSize());
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

    //综合帖子
    private BoolQueryBuilder buildCommunityPostCondition(SearchSourceBuilder sourceBuilder, EsSearchDTO esSearchDTO) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (esSearchDTO.getUserId() != null) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("userId", esSearchDTO.getUserId().toString());
            boolQueryBuilder.must(queryBuilder);
        }
        if (esSearchDTO.getId() != null) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("id", esSearchDTO.getUserId().toString());
            boolQueryBuilder.must(queryBuilder);
        }
        //帖子 标题完全匹配 正文匹配分
        if (StrUtil.isNotEmpty(esSearchDTO.getContents())) {
            sourceBuilder.sort("id", SortOrder.DESC);
            QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("title", esSearchDTO.getContents());
            boolQueryBuilder.should(queryBuilder);
            if (esSearchDTO.getContents().length() < 3) {
                return boolQueryBuilder;
            }
            QueryBuilder queryBuilder1 = QueryBuilders.matchQuery("contents", esSearchDTO.getContents());
            boolQueryBuilder.should(queryBuilder1);
        }
        return boolQueryBuilder;
    }

    private BoolQueryBuilder buildArticleCondition(SearchSourceBuilder sourceBuilder, EsSearchDTO esSearchDTO) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (esSearchDTO.getUserId() != null) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("userId", esSearchDTO.getUserId().toString());
            boolQueryBuilder.must(queryBuilder);
        }
        if (esSearchDTO.getId() != null) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("id", esSearchDTO.getUserId().toString());
            boolQueryBuilder.must(queryBuilder);
        }
        if (StrUtil.isNotEmpty(esSearchDTO.getContents())) {
            sourceBuilder.sort("id", SortOrder.DESC);
            QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("title", esSearchDTO.getContents());
            boolQueryBuilder.should(queryBuilder);
            if (esSearchDTO.getContents().length() < 3) {
                return boolQueryBuilder;
            }
            QueryBuilder queryBuilder1 = QueryBuilders.matchQuery("details", esSearchDTO.getContents());
            boolQueryBuilder.should(queryBuilder1);
        }
        return boolQueryBuilder;
    }

    private BoolQueryBuilder buildProductCondition(SearchSourceBuilder sourceBuilder, EsSearchDTO esSearchDTO) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (esSearchDTO.getId() != null) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("id", esSearchDTO.getUserId().toString());
            boolQueryBuilder.must(queryBuilder);
        }
        if (StrUtil.isNotEmpty(esSearchDTO.getContents())) {
            sourceBuilder.sort("id", SortOrder.DESC);
            QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("productName", esSearchDTO.getContents());
            boolQueryBuilder.should(queryBuilder);
        }
        return boolQueryBuilder;
    }

    private BoolQueryBuilder buildProductCommentCondition(SearchSourceBuilder sourceBuilder, EsSearchDTO esSearchDTO) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (esSearchDTO.getUserId() != null) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("userId", esSearchDTO.getUserId().toString());
            boolQueryBuilder.must(queryBuilder);
        }
        if (esSearchDTO.getId() != null) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("id", esSearchDTO.getUserId().toString());
            boolQueryBuilder.must(queryBuilder);
        }
        if (StrUtil.isNotEmpty(esSearchDTO.getContents())) {
            sourceBuilder.sort("id", SortOrder.DESC);
            QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("details", esSearchDTO.getContents());
            boolQueryBuilder.should(queryBuilder);
        }
        return boolQueryBuilder;
    }

    @Override
    public void deleteOne(String idxName, String type, Long id) {
        DeleteRequest request = new DeleteRequest(idxName, type, id.toString());
        try {
            restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void productUpdate(String indexName, ProductEsDTO productEsDTO) {
        if (productEsDTO.getId() == null) {
            log.error("id is null");
            return;
        }
        UpdateByQueryRequest request = new UpdateByQueryRequest(indexName);
        //多字段更新 TODO
        if (StrUtil.isNotEmpty(productEsDTO.getProductName())) {
            request.setScript(new Script(productEsDTO.getProductName()));
        }
        update(request, productEsDTO.getId());
    }

    @Override
    public void communityPostUpdate(String indexName, CommunityPostEsDTO communityPostEsDTO) {
        if (communityPostEsDTO.getId() == null) {
            log.error("id is null");
            return;
        }
        UpdateByQueryRequest request = new UpdateByQueryRequest(indexName);
        //多字段更新 TODO
        if (StrUtil.isNotEmpty(communityPostEsDTO.getContents())) {
            request.setScript(new Script(communityPostEsDTO.getContents()));
        }
        update(request, communityPostEsDTO.getId());
    }


    private void update(UpdateByQueryRequest request, Long id) {
        // 更新时版本冲突
        request.setConflicts("proceed");
        //request.setSize(10);
        //request.setBatchSize(100);
        request.setRefresh(true);
        BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
        mustQuery.must(QueryBuilders.termQuery("id", id));
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
