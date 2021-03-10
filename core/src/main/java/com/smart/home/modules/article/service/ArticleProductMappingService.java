package com.smart.home.modules.article.service;

import com.smart.home.modules.article.dao.ArticleProductMappingMapper;
import com.smart.home.modules.article.entity.ArticleProductMapping;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jason
 **/
@Service
public class ArticleProductMappingService {

    @Resource
    ArticleProductMappingMapper articleProductMappingMapper;

    public int create(Long articleId, Integer productId, String testResult, Integer recommendFlag) {
        ArticleProductMapping articleProductMapping = new ArticleProductMapping();
        articleProductMapping.withArticleId(articleId)
                .withProductId(productId)
                .withTestResult(testResult)
                .withRecommendFlag(recommendFlag);
        return articleProductMappingMapper.insertSelective(articleProductMapping);
    }

    public ArticleProductMapping findByArticle(Long id) {
        return articleProductMappingMapper.findByArticleId(id);
    }
}
