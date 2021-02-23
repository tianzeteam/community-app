package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductShopMapper;
import com.smart.home.modules.product.entity.ProductShop;
import com.smart.home.modules.product.entity.ProductShopExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductShopService {

    @Resource
    ProductShopMapper productShopMapper;

    public int create(ProductShop productShop) {
        productShop.setCreatedTime(new Date());
        return productShopMapper.insertSelective(productShop);
    }

    public int update(ProductShop productShop) {
        productShop.setUpdatedTime(new Date());
        return productShopMapper.updateByPrimaryKeySelective(productShop);
    }

    public int deleteById(Long id) {
        return productShopMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productShopMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<ProductShop> selectByPage(ProductShop productShop, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductShopExample example = new ProductShopExample();
        ProductShopExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productShopMapper.selectByExample(example);
    }

    public ProductShop findById(Long id) {
        ProductShop productShop = productShopMapper.selectByPrimaryKey(id.intValue());
        return productShop;
    }

}
