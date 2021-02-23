package com.smart.home.modules.community.dao;

import com.smart.home.modules.community.entity.CommunityCategory;
import com.smart.home.modules.community.entity.CommunityCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityCategoryMapper {
    long countByExample(CommunityCategoryExample example);

    int deleteByExample(CommunityCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommunityCategory record);

    int insertSelective(CommunityCategory record);

    List<CommunityCategory> selectByExample(CommunityCategoryExample example);

    CommunityCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommunityCategory record, @Param("example") CommunityCategoryExample example);

    int updateByExample(@Param("record") CommunityCategory record, @Param("example") CommunityCategoryExample example);

    int updateByPrimaryKeySelective(CommunityCategory record);

    int updateByPrimaryKey(CommunityCategory record);
}