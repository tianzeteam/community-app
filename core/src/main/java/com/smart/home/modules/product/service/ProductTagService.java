package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductTagMapper;
import com.smart.home.modules.product.entity.ProductTag;
import com.smart.home.modules.product.entity.ProductTagExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductTagService {

    @Resource
    ProductTagMapper productTagMapper;

    public int create(ProductTag productTag) {
        return productTagMapper.insertSelective(productTag);
    }

    public int update(ProductTag productTag) {
        return productTagMapper.updateByPrimaryKeySelective(productTag);
    }

    public int deleteById(Long id) {
        return productTagMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productTagMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<ProductTag> selectByPage(ProductTag productTag, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductTagExample example = new ProductTagExample();
        ProductTagExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productTagMapper.selectByExample(example);
    }

    public ProductTag findById(Long id) {
        ProductTag productTag = productTagMapper.selectByPrimaryKey(id.intValue());
        return productTag;
    }

}
