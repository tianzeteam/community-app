package com.smart.home.es.service;

import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.BulkOptions;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/18
 **/
@Service
public class EsUpdateService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    /**
     * 单索引匹配更新
     * @param updateKey 需要更新的字段
     * @param updateValue 更新后的字段值
     * @param indexName 索引名称
     * @param queryKey 查询出某个记录进行更新
     * @param queryValue
     * @throws IOException
     */
    public void updateSourceValue(String updateKey, Object updateValue, String indexName, String queryKey, Object queryValue) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(updateKey, updateValue);
        //ctx._source即为该索引本身
        String code = "ctx._source."+updateKey+"=params."+updateKey+";";
        ScriptType type = ScriptType.INLINE;
        //使用脚本进行更新字段值
        Script script = new Script(type, Script.DEFAULT_SCRIPT_LANG, code, params);
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
        //设置索引
        updateByQueryRequest.indices(indexName);
        //设置文档，固定doc
        updateByQueryRequest.setDocTypes("doc");
        //设置查询
        updateByQueryRequest.setQuery(QueryBuilders.termsQuery(queryKey, queryValue));
        //如果有脚本，则添加
        updateByQueryRequest.setScript(script);
        // 设置版本冲突时继续
        updateByQueryRequest.setConflicts("proceed");
        //请求结束后，对我们写入的索引进行调用刷新
        updateByQueryRequest.setRefresh(true);
        //进行更新
        this.elasticsearchTemplate.getClient().updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
    }

    /**
     * 多索引匹配批量更新
     * @param updateParams 需要更新的key-value对
     * @param indexName 索引名称
     * @param idList 索引id值
     */
    public void updateSourceValues(Map<String, Object> updateParams, String indexName, List<String> idList) {
        UpdateRequest doc = new UpdateRequest().doc(updateParams);
        List<UpdateQuery> updateQuerys = new ArrayList<>();
        //生成批量更新操作
        idList.stream().forEach(id ->{
        UpdateQuery build = new UpdateQueryBuilder()
                             .withUpdateRequest(doc)
                             .withDoUpsert(true)
                             .withIndexName(indexName)
                             .withType("doc")
                             .withId(id).build();
                     updateQuerys.add(build);
                 });
        elasticsearchTemplate.bulkUpdate(updateQuerys, BulkOptions.builder().withRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE).build());
    }

}
