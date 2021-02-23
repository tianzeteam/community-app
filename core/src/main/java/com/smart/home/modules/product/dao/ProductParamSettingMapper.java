package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductParamSetting;
import com.smart.home.modules.product.entity.ProductParamSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductParamSettingMapper {
    long countByExample(ProductParamSettingExample example);

    int deleteByExample(ProductParamSettingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductParamSetting record);

    int insertSelective(ProductParamSetting record);

    List<ProductParamSetting> selectByExample(ProductParamSettingExample example);

    ProductParamSetting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductParamSetting record, @Param("example") ProductParamSettingExample example);

    int updateByExample(@Param("record") ProductParamSetting record, @Param("example") ProductParamSettingExample example);

    int updateByPrimaryKeySelective(ProductParamSetting record);

    int updateByPrimaryKey(ProductParamSetting record);
}