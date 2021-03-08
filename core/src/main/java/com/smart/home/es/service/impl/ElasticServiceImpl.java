package com.smart.home.es.service.impl;

import com.smart.home.common.util.DateUtils;
import com.smart.home.common.util.UUIDUtil;
import com.smart.home.es.bean.DocBean;
import com.smart.home.es.bean.SearchKey;
import com.smart.home.es.dao.ElasticRepository;
import com.smart.home.es.dao.SearchKeyRepository;
import com.smart.home.es.service.ElasticService;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/17
 **/
@Service
public class ElasticServiceImpl implements ElasticService {

    @Autowired
    private ElasticRepository elasticRepository;
    @Autowired
    private SearchKeyRepository searchKeyRepository;

    private Pageable pageable = PageRequest.of(0, 10);

    @Override
    public void save(DocBean docBean) {
        elasticRepository.save(docBean);
    }

    @Override
    public void saveAll(List<DocBean> list) {
        elasticRepository.saveAll(list);
    }

    @Override
    public Iterator<SearchKey> findAll() {
        return searchKeyRepository.findAll().iterator();
    }

    @Override
    public Page<DocBean> findByContent(String content) {
        return elasticRepository.findByContent(content, pageable);
    }

    @Override
    public Page<DocBean> findByFirstCode(String firstCode) {
        return elasticRepository.findByFirstCode(firstCode, pageable);
    }

    @Override
    public Page<DocBean> findBySecordCode(String secordCode) {
        return elasticRepository.findBySecordCode(secordCode, pageable);
    }

    @Override
    public Page<DocBean> query(String key) {
        return elasticRepository.findByContent(key, pageable);
    }

    @Override
    public void saveKeyword(String keyword) {
        Asserts.notBlank(keyword, "keyword");
        String id = UUIDUtil.uuid();
        SearchKey searchKey = new SearchKey(id, keyword, DateUtils.getCurrentDateTime());
        searchKeyRepository.save(searchKey);
    }

}
