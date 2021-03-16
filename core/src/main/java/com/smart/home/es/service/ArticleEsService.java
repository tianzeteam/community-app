package com.smart.home.es.service;

import com.smart.home.es.bean.ArticleBean;

/**
 * @author jason
 * @date 2021/3/16
 **/
public interface ArticleEsService {
    void save(ArticleBean articleBean);

    void deleteById(Long id);
}
