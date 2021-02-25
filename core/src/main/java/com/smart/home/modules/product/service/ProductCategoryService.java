package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.modules.product.dao.ProductCategoryMapper;
import com.smart.home.modules.product.entity.ProductCategory;
import com.smart.home.modules.product.entity.ProductCategoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductCategoryService {

    @Resource
    ProductCategoryMapper productCategoryMapper;

    public int create(ProductCategory productCategory) {
        productCategory.setCreatedTime(new Date());
        return productCategoryMapper.insertSelective(productCategory);
    }

    public int update(ProductCategory productCategory) {
        productCategory.setUpdatedTime(new Date());
        return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }

    public int deleteById(Long id) {
        return productCategoryMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productCategoryMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<ProductCategory> selectByPage(ProductCategory productCategory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductCategoryExample example = new ProductCategoryExample();
        ProductCategoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productCategoryMapper.selectByExample(example);
    }

    public ProductCategory findById(Long id) {
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(id.intValue());
        return productCategory;
    }

    public List<ProductCategory> queryAllValidByPid(int pid) {
        ProductCategoryExample example = new ProductCategoryExample();
        example.createCriteria().andPidEqualTo(pid).andStateEqualTo(RecordStatusEnum.NORMAL.getStatus());
        return productCategoryMapper.selectByExample(example);
    }
}
