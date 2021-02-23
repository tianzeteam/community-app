package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductBrandMapper;
import com.smart.home.modules.product.entity.ProductBrand;
import com.smart.home.modules.product.entity.ProductBrandExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductBrandService {

    @Resource
    ProductBrandMapper productBrandMapper;

    public int create(ProductBrand productBrand) {
        productBrand.setCreatedTime(new Date());
        return productBrandMapper.insertSelective(productBrand);
    }

    public int update(ProductBrand productBrand) {
        productBrand.setUpdatedTime(new Date());
        return productBrandMapper.updateByPrimaryKeySelective(productBrand);
    }

    public int deleteById(Long id) {
        return productBrandMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productBrandMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<ProductBrand> selectByPage(ProductBrand productBrand, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductBrandExample example = new ProductBrandExample();
        ProductBrandExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productBrandMapper.selectByExample(example);
    }

    public ProductBrand findById(Long id) {
        ProductBrand productBrand = productBrandMapper.selectByPrimaryKey(id.intValue());
        return productBrand;
    }

}
