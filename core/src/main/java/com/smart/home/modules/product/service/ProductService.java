package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductMapper;
import com.smart.home.modules.product.entity.Product;
import com.smart.home.modules.product.entity.ProductExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductService {

    @Resource
    ProductMapper productMapper;

    public int create(Product product) {
        product.setCreatedTime(new Date());
        return productMapper.insertSelective(product);
    }

    public int update(Product product) {
        product.setUpdatedTime(new Date());
        return productMapper.updateByPrimaryKeySelective(product);
    }

    public int deleteById(Long id) {
        return productMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<Product> selectByPage(Product product, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productMapper.selectByExample(example);
    }

    public Product findById(Long id) {
        Product product = productMapper.selectByPrimaryKey(id.intValue());
        return product;
    }

}
