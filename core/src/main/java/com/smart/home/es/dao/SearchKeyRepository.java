package com.smart.home.es.dao;

import com.smart.home.es.bean.SearchKey;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchKeyRepository extends ElasticsearchRepository<SearchKey, String> {
}
