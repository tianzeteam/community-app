package com.smart.home.modules.community.dao;

import com.smart.home.modules.community.entity.CommunityUserMapping;
import com.smart.home.modules.community.entity.CommunityUserMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityUserMappingMapper {
    long countByExample(CommunityUserMappingExample example);

    int deleteByExample(CommunityUserMappingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommunityUserMapping record);

    int insertSelective(CommunityUserMapping record);

    List<CommunityUserMapping> selectByExample(CommunityUserMappingExample example);

    CommunityUserMapping selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommunityUserMapping record, @Param("example") CommunityUserMappingExample example);

    int updateByExample(@Param("record") CommunityUserMapping record, @Param("example") CommunityUserMappingExample example);

    int updateByPrimaryKeySelective(CommunityUserMapping record);

    int updateByPrimaryKey(CommunityUserMapping record);
}