package com.smart.home.es.dao;

import com.smart.home.es.bean.ProductCommentBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jason
 * @date 2021/3/8
 **/
@Repository
public interface ProductCommentRepository extends ElasticsearchRepository<ProductCommentBean, Long> {
}
