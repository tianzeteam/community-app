package com.smart.home.es.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

/**
 * @author jason
 * @date 2021/2/18
 **/
@Service
public class EsIndexService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    /**
     * 创建索引
     * @param T
     * @param <T>
     * @return
     */
    public <T> boolean createIndex(Class<T> T) {
        boolean success = elasticsearchTemplate.createIndex(T);
        if (success) {
            success = elasticsearchTemplate.putMapping(T);
        }
        return success;
    }

    /**
     * 删除索引
     * @param T
     * @param <T>
     * @return
     */
    public <T> boolean deleteIndex(Class<T> T) {
        return elasticsearchTemplate.deleteIndex(T);
    }

    public boolean deleteIndexByName(String indexName) {
        return elasticsearchTemplate.deleteIndex(indexName);
    }
}
