package com.smart.home.es.dao;

import com.smart.home.es.bean.ProductCommentBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author jason
 * @date 2021/3/8
 **/
public interface ProductCommentRepository extends ElasticsearchRepository<ProductCommentBean, Long> {
}
