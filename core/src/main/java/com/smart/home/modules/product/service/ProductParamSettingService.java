package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.modules.product.dao.ProductParamSettingMapper;
import com.smart.home.modules.product.dao.ProductParamValueMapper;
import com.smart.home.modules.product.entity.ProductParamSetting;
import com.smart.home.modules.product.entity.ProductParamSettingExample;
import org.apache.commons.lang3.StringUtils;
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
    @Resource
    ProductParamValueMapper productParamValueMapper;

    public int create(ProductParamSetting productParamSetting) {
        ProductParamSettingExample example = new ProductParamSettingExample();
        example.createCriteria().andParamNameEnEqualTo(productParamSetting.getParamName());
        if (productParamSettingMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该参数已经存在");
        }
        productParamSetting.setRevision(0);
        productParamSetting.setCreatedTime(new Date());
        return productParamSettingMapper.insertSelective(productParamSetting);
    }

    public int update(ProductParamSetting productParamSetting) {
        ProductParamSettingExample example = new ProductParamSettingExample();
        example.createCriteria()
                .andParamNameEnEqualTo(productParamSetting.getParamName())
                .andIdNotEqualTo(productParamSetting.getId());
        if (productParamSettingMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该参数已经存在");
        }
        productParamSetting.setUpdatedTime(new Date());
        return productParamSettingMapper.updateByPrimaryKeySelective(productParamSetting);
    }

    public int deleteById(Long id) {
        return productParamSettingMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            // 判断有没有关联产品了，关联产品后不能删除
            if (productParamValueMapper.countByParamId(id.intValue()) > 0) {
                throw new ServiceException("已经关联了产品，不能删除");
            }
            productParamSettingMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<ProductParamSetting> selectByPage(ProductParamSetting productParamSetting, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductParamSettingExample example = new ProductParamSettingExample();
        ProductParamSettingExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(productParamSetting.getParamName())) {
            criteria.andParamNameEqualTo(productParamSetting.getParamName());
        }
        return productParamSettingMapper.selectByExample(example);
    }

    public ProductParamSetting findById(Long id) {
        ProductParamSetting productParamSetting = productParamSettingMapper.selectByPrimaryKey(id.intValue());
        return productParamSetting;
    }

    public List<ProductParamSetting> queryAllValid() {
        ProductParamSettingExample example = new ProductParamSettingExample();
        return productParamSettingMapper.selectByExample(example);
    }
}
