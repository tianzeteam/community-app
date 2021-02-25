package com.smart.home.es.dao;

import com.smart.home.es.bean.CommunityBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends ElasticsearchRepository<CommunityBean, Integer>
{
}
