package com.smart.home.es.dao;

import com.smart.home.es.bean.SearchKey;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchKeyRepository extends ElasticsearchRepository<SearchKey, String> {
}
