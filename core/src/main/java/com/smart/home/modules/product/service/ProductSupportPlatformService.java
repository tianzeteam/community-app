package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductSupportPlatformMapper;
import com.smart.home.modules.product.entity.ProductSupportPlatform;
import com.smart.home.modules.product.entity.ProductSupportPlatformExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author jason
**/
@Service
public class ProductSupportPlatformService {

    @Resource
    ProductSupportPlatformMapper productSupportPlatformMapper;

    public int create(Integer productId, String platformName) {
        ProductSupportPlatform productSupportPlatform = new ProductSupportPlatform();
        productSupportPlatform.withProductId(productId).withSupportPlatform(platformName);
        return productSupportPlatformMapper.insertSelective(productSupportPlatform);
    }

    public ProductSupportPlatform findById(Long id) {
        ProductSupportPlatform productSupportPlatform = productSupportPlatformMapper.selectByPrimaryKey(id);
        return productSupportPlatform;
    }

    public void deleteByProductId(Integer productId) {
        ProductSupportPlatformExample example = new ProductSupportPlatformExample();
        example.createCriteria().andProductIdEqualTo(productId);
        productSupportPlatformMapper.deleteByExample(example);
    }
}
