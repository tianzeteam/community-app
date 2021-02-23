package com.smart.home.modules.other.dao;

import com.smart.home.modules.other.entity.SubjectCard;
import com.smart.home.modules.other.entity.SubjectCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SubjectCardMapper {
    long countByExample(SubjectCardExample example);

    int deleteByExample(SubjectCardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SubjectCard record);

    int insertSelective(SubjectCard record);

    List<SubjectCard> selectByExample(SubjectCardExample example);

    SubjectCard selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SubjectCard record, @Param("example") SubjectCardExample example);

    int updateByExample(@Param("record") SubjectCard record, @Param("example") SubjectCardExample example);

    int updateByPrimaryKeySelective(SubjectCard record);

    int updateByPrimaryKey(SubjectCard record);
}