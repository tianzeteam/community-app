package com.smart.home.modules.system.dao;

import com.smart.home.modules.system.entity.SysAgreement;
import com.smart.home.modules.system.entity.SysAgreementExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysAgreementMapper {
    long countByExample(SysAgreementExample example);

    int deleteByExample(SysAgreementExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysAgreement record);

    int insertSelective(SysAgreement record);

    List<SysAgreement> selectByExample(SysAgreementExample example);

    SysAgreement selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysAgreement record, @Param("example") SysAgreementExample example);

    int updateByExample(@Param("record") SysAgreement record, @Param("example") SysAgreementExample example);

    int updateByPrimaryKeySelective(SysAgreement record);

    int updateByPrimaryKey(SysAgreement record);
}