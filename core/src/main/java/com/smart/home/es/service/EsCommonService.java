package com.smart.home.es.service;

import com.smart.home.es.dto.CommunityPostEsDTO;
import com.smart.home.es.dto.EsSearchDTO;
import com.smart.home.es.dto.ProductEsDTO;

import java.util.List;

/**
 * highlevel形式使用ES  推荐
 */
public interface EsCommonService<T> {

    /**
     * 保存或更新
     */
    void insertOrUpdateOne(String idxName, String type, Long id, T t);

    /**
     * 多索引查询
     */
    <T> List<T> searchMultiple(String[] idxNames, EsSearchDTO esSearchDTO, Class<T> c);

    /**
     * 单索引，多条件查询
     */
    List<T> search(String indexName, EsSearchDTO esSearchDTO, Class<T> c);

    /**
     * 根据id 删除
     */
    void deleteOne(String idxName, String type, Long id);

    /**
     * 产品 更新
     */
    void productUpdate(String indexName, ProductEsDTO productEsDTO);

    /**
     * 社区帖子 更新
     */
    void communityPostUpdate(String indexName, CommunityPostEsDTO communityPostEsDTO);

}
