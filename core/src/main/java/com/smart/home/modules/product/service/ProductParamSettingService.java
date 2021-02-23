package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductParamSettingMapper;
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
public class ProductParamSettingService {

    @Resource
    ProductParamSettingMapper productParamSettingMapper;

    public int create(ProductParamSetting productParamSetting) {
        productParamSetting.setCreatedTime(new Date());
        return productParamSettingMapper.insertSelective(productParamSetting);
    }

    public int update(ProductParamSetting productParamSetting) {
        productParamSetting.setUpdatedTime(new Date());
        return productParamSettingMapper.updateByPrimaryKeySelective(productParamSetting);
    }

    public int deleteById(Long id) {
        return productParamSettingMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productParamSettingMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<ProductParamSetting> selectByPage(ProductParamSetting productParamSetting, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductParamSettingExample example = new ProductParamSettingExample();
        ProductParamSettingExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productParamSettingMapper.selectByExample(example);
    }

    public ProductParamSetting findById(Long id) {
        ProductParamSetting productParamSetting = productParamSettingMapper.selectByPrimaryKey(id.intValue());
        return productParamSetting;
    }

}
