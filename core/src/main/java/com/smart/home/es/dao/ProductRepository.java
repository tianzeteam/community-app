package com.smart.home.es.dao;

import com.smart.home.es.bean.ProductBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jason
 * @date 2021/3/15
 **/
@Repository
public interface ProductRepository extends ElasticsearchRepository<ProductBean, Integer> {
}
