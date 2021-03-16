package com.smart.home.es.service.impl;

import com.smart.home.es.bean.ArticleBean;
import com.smart.home.es.dao.ArticleRepository;
import com.smart.home.es.service.ArticleEsService;
import com.smart.home.es.service.EsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jason
 * @date 2021/3/16
 **/
@Service
public class ArticleEsServiceImpl extends EsQueryService implements ArticleEsService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void save(ArticleBean articleBean) {
        articleRepository.save(articleBean);
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }
}
