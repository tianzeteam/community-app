package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductShopMappingMapper;
import com.smart.home.modules.product.entity.ProductShopMapping;
import com.smart.home.modules.product.entity.ProductShopMappingExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductShopMappingService {

    @Resource
    ProductShopMappingMapper productShopMappingMapper;

    public int create(ProductShopMapping productShopMapping) {
        return productShopMappingMapper.insertSelective(productShopMapping);
    }

    public int update(ProductShopMapping productShopMapping) {
        return productShopMappingMapper.updateByPrimaryKeySelective(productShopMapping);
    }

    public int deleteById(Long id) {
        return productShopMappingMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productShopMappingMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ProductShopMapping> selectByPage(ProductShopMapping productShopMapping, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductShopMappingExample example = new ProductShopMappingExample();
        ProductShopMappingExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productShopMappingMapper.selectByExample(example);
    }

    public ProductShopMapping findById(Long id) {
        ProductShopMapping productShopMapping = productShopMappingMapper.selectByPrimaryKey(id);
        return productShopMapping;
    }

}
