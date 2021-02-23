package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductCategoryTagMapper;
import com.smart.home.modules.product.entity.ProductCategoryTag;
import com.smart.home.modules.product.entity.ProductCategoryTagExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductCategoryTagService {

    @Resource
    ProductCategoryTagMapper productCategoryTagMapper;

    public int create(ProductCategoryTag productCategoryTag) {
        return productCategoryTagMapper.insertSelective(productCategoryTag);
    }

    public int update(ProductCategoryTag productCategoryTag) {
        return productCategoryTagMapper.updateByPrimaryKeySelective(productCategoryTag);
    }

    public int deleteById(Long id) {
        return productCategoryTagMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productCategoryTagMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<ProductCategoryTag> selectByPage(ProductCategoryTag productCategoryTag, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductCategoryTagExample example = new ProductCategoryTagExample();
        ProductCategoryTagExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productCategoryTagMapper.selectByExample(example);
    }

    public ProductCategoryTag findById(Long id) {
        ProductCategoryTag productCategoryTag = productCategoryTagMapper.selectByPrimaryKey(id.intValue());
        return productCategoryTag;
    }

}
