package com.smart.home.modules.product.service;

import cn.hutool.core.collection.CollUtil;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.modules.product.dao.ProductCategoryParamMapper;
import com.smart.home.modules.product.dao.ProductParamSettingMapper;
import com.smart.home.modules.product.entity.ProductCategoryParam;
import com.smart.home.modules.product.entity.ProductCategoryParamExample;
import com.smart.home.modules.product.entity.ProductParamSetting;
import com.smart.home.modules.product.entity.ProductParamSettingExample;
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
    @Resource
    ProductParamSettingMapper productParamSettingMapper;

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

    public ProductCategoryParam findById(Long id) {
        ProductCategoryParam productCategoryParam = productCategoryParamMapper.selectByPrimaryKey(id);
        return productCategoryParam;
    }

    public void create(int productCategoryId, Integer productParamId, Integer sort) {
        ProductCategoryParam productCategoryParam = new ProductCategoryParam();
        productCategoryParam.withMandatoryFlag(YesNoEnum.NO.getCode())
                .withProductCategoryId(productCategoryId)
                .withProductParamId(productParamId)
                .withSort(sort);
        create(productCategoryParam);
    }

    public void deleteByProductCategoryId(Integer productCategoryId) {
        ProductCategoryParamExample example = new ProductCategoryParamExample();
        example.createCriteria().andProductCategoryIdEqualTo(productCategoryId);
        this.productCategoryParamMapper.deleteByExample(example);
    }

    public List<Integer> findParamIdListByCategoryId(int categoryId) {
        return this.productCategoryParamMapper.findParamIdListByCategoryId(categoryId);
    }

    public List<ProductParamSetting> findParamListByCategoryId(int categoryId) {
        List<Integer> idList = findParamIdListByCategoryId(categoryId);
        if (CollUtil.isNotEmpty(idList)) {
            ProductParamSettingExample example = new ProductParamSettingExample();
            example.createCriteria().andIdIn(idList).andEnableAllEqualTo(YesNoEnum.NO.getCode());
            return productParamSettingMapper.selectByExample(example);
        }
        return null;
    }
}
