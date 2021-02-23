package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductCategoryParamMapper;
import com.smart.home.modules.product.entity.ProductCategoryParam;
import com.smart.home.modules.product.entity.ProductCategoryParamExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductCategoryParamService {

    @Resource
    ProductCategoryParamMapper productCategoryParamMapper;

    public int create(ProductCategoryParam productCategoryParam) {
        return productCategoryParamMapper.insertSelective(productCategoryParam);
    }

    public int update(ProductCategoryParam productCategoryParam) {
        return productCategoryParamMapper.updateByPrimaryKeySelective(productCategoryParam);
    }

    public int deleteById(Long id) {
        return productCategoryParamMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productCategoryParamMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ProductCategoryParam> selectByPage(ProductCategoryParam productCategoryParam, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductCategoryParamExample example = new ProductCategoryParamExample();
        ProductCategoryParamExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productCategoryParamMapper.selectByExample(example);
    }

    public ProductCategoryParam findById(Long id) {
        ProductCategoryParam productCategoryParam = productCategoryParamMapper.selectByPrimaryKey(id);
        return productCategoryParam;
    }

}
