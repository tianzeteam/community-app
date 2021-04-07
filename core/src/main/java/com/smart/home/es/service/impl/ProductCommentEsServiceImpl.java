package com.smart.home.es.service.impl;

import com.smart.home.es.bean.ProductCommentBean;
import com.smart.home.es.dao.ProductCommentRepository;
import com.smart.home.es.service.EsQueryService;
import com.smart.home.es.service.ProductCommentEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jason
 * @date 2021/3/8
 **/
@Service
public class ProductCommentEsServiceImpl extends EsQueryService implements ProductCommentEsService {

    @Autowired
    private ProductCommentRepository productCommentRepository;

    @Override
    public void save(ProductCommentBean productCommentBean) {
        productCommentRepository.save(productCommentBean);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByProductId(Integer productId) {
        return productCommentRepository.deleteByProductId(productId);
    }
}
