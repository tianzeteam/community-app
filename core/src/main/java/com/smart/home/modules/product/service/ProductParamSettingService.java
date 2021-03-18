package com.smart.home.modules.product.service;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.modules.product.dao.ProductCategoryParamMapper;
import com.smart.home.modules.product.dao.ProductParamSettingMapper;
import com.smart.home.modules.product.dao.ProductParamValueMapper;
import com.smart.home.modules.product.entity.ProductParamSetting;
import com.smart.home.modules.product.entity.ProductParamSettingExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author jason
 **/
@Service
public class ProductParamSettingService {

    @Resource
    ProductParamSettingMapper productParamSettingMapper;
    @Resource
    ProductParamValueMapper productParamValueMapper;
    @Resource
    ProductCategoryParamMapper productCategoryParamMapper;

    public int create(ProductParamSetting productParamSetting) throws ServiceException {
        ProductParamSettingExample example = new ProductParamSettingExample();
        example.createCriteria().andParamNameEnEqualTo(productParamSetting.getParamName());
        if (productParamSettingMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该参数已经存在");
        }
        productParamSetting.setRevision(0);
        productParamSetting.setCreatedTime(new Date());
        if (Objects.isNull(productParamSetting.getEnableAll())) {
            productParamSetting.setEnableAll(YesNoEnum.NO.getCode());
        }
        return productParamSettingMapper.insertSelective(productParamSetting);
    }

    public int update(ProductParamSetting productParamSetting) throws ServiceException {
        ProductParamSettingExample example = new ProductParamSettingExample();
        example.createCriteria()
                .andParamNameEnEqualTo(productParamSetting.getParamName())
                .andIdNotEqualTo(productParamSetting.getId());
        if (productParamSettingMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该参数已经存在");
        }
        if (Objects.isNull(productParamSetting.getEnableAll())) {
            productParamSetting.setEnableAll(YesNoEnum.NO.getCode());
        }
        productParamSetting.setUpdatedTime(new Date());
        return productParamSettingMapper.updateByPrimaryKeySelective(productParamSetting);
    }

    public int deleteById(Long id) {
        return productParamSettingMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) throws ServiceException {
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
        if (productParamSetting.getEnableAll() != null) {
            criteria.andEnableAllEqualTo(productParamSetting.getEnableAll());
        }
        return productParamSettingMapper.selectByExample(example);
    }

    public ProductParamSetting findById(Integer id) {
        ProductParamSetting productParamSetting = productParamSettingMapper.selectByPrimaryKey(id);
        return productParamSetting;
    }

    public List<ProductParamSetting> queryAllValid() {
        ProductParamSettingExample example = new ProductParamSettingExample();
        example.createCriteria().andEnableAllIn(Arrays.asList(YesNoEnum.NO.getCode(), YesNoEnum.YES.getCode()));
        return productParamSettingMapper.selectByExample(example);
    }

    public List<ProductParamSetting> queryAllValidExceptEnableAll() {
        ProductParamSettingExample example = new ProductParamSettingExample();
        example.createCriteria().andEnableAllEqualTo(YesNoEnum.NO.getCode());
        return productParamSettingMapper.selectByExample(example);
    }

    public List<ProductParamSetting> queryAllValidWithEnableAll() {
        ProductParamSettingExample example = new ProductParamSettingExample();
        example.createCriteria().andEnableAllEqualTo(YesNoEnum.YES.getCode());
        return productParamSettingMapper.selectByExample(example);
    }

    public List<ProductParamSetting> queryAllValidExceptEnableAll(Integer categoryId) {
        List<Integer> paramIdList = productCategoryParamMapper.findParamIdListByCategoryId(categoryId);
        ProductParamSettingExample example = new ProductParamSettingExample();
        example.createCriteria().andIdIn(paramIdList);
        return productParamSettingMapper.selectByExample(example);
    }

    public List<ProductParamSetting> queryByIdList(List<Integer> paramIdList) {
        if (CollUtil.isEmpty(paramIdList)) {
            return Collections.EMPTY_LIST;
        }
        ProductParamSettingExample example = new ProductParamSettingExample();
        example.createCriteria().andIdIn(paramIdList);
        return productParamSettingMapper.selectByExample(example);
    }
}
