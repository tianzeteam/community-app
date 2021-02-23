package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductParamValueMapper;
import com.smart.home.modules.product.entity.ProductParamValue;
import com.smart.home.modules.product.entity.ProductParamValueExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductParamValueService {

    @Resource
    ProductParamValueMapper productParamValueMapper;

    public int create(ProductParamValue productParamValue) {
        return productParamValueMapper.insertSelective(productParamValue);
    }

    public int update(ProductParamValue productParamValue) {
        return productParamValueMapper.updateByPrimaryKeySelective(productParamValue);
    }

    public int deleteById(Long id) {
        return productParamValueMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productParamValueMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ProductParamValue> selectByPage(ProductParamValue productParamValue, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductParamValueExample example = new ProductParamValueExample();
        ProductParamValueExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productParamValueMapper.selectByExample(example);
    }

    public ProductParamValue findById(Long id) {
        ProductParamValue productParamValue = productParamValueMapper.selectByPrimaryKey(id);
        return productParamValue;
    }

}
