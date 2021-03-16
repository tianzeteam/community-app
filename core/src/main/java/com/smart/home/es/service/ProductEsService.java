package com.smart.home.es.service;

import com.smart.home.es.bean.ProductBean;

/**
 * @author jason
 * @date 2021/3/16
 **/
public interface ProductEsService {

    void deleteById (Integer id);

    void save(ProductBean productBean);

    void update(ProductBean productBean);
}
