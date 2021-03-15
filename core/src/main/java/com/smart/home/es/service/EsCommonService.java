package com.smart.home.es.service;

import com.smart.home.es.dto.EsSearchDTO;

import java.util.List;

/**
 * highlevel形式使用ES  推荐
 */
public interface EsCommonService<T> {


    /**
     * 保存或更新
     */
    void insertOrUpdateOne(String idxName, T t);

    /**
     * 综合查询  多索引查询
     */
    <T> List<T> searchMultiple(String[] idxNames, EsSearchDTO esSearchDTO, Class<T> c);


}
