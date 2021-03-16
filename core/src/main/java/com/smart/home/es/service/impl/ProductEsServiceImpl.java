package com.smart.home.es.service.impl;

import com.smart.home.es.bean.ProductBean;
import com.smart.home.es.dao.ProductRepository;
import com.smart.home.es.service.EsQueryService;
import com.smart.home.es.service.ProductEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jason
 * @date 2021/3/16
 **/
@Service
public class ProductEsServiceImpl extends EsQueryService implements ProductEsService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public void save(ProductBean productBean) {
        productRepository.save(productBean);
    }

    @Override
    public void update(ProductBean productBean) {
        productRepository.save(productBean);
    }
}
