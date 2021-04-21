package com.smart.home.es.dao;

import com.smart.home.es.bean.ArticleBean;
import com.smart.home.es.bean.CommunityPostBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: lǎo xiāng
 * @Date: 2021/4/21 下午7:13
 * @Describe:
 */
@Repository
public interface CommunityPostRepository extends ElasticsearchRepository<CommunityPostBean, Long> {

}
