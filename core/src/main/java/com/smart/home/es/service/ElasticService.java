package com.smart.home.es.service;

import com.smart.home.es.bean.DocBean;
import com.smart.home.es.bean.SearchKey;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/17
 **/
public interface ElasticService {

    void save(DocBean docBean);

    void saveAll(List<DocBean> list);

    Iterator<SearchKey> findAll();

    Page<DocBean> findByContent(String content);

    Page<DocBean> findByFirstCode(String firstCode);

    Page<DocBean> findBySecordCode(String secordCode);

    Page<DocBean> query(String key);

    void saveKeyword(String keyword);

}
