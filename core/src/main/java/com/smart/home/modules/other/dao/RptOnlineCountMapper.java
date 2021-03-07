package com.smart.home.modules.other.dao;

import com.smart.home.modules.other.entity.RptOnlineCount;
import com.smart.home.modules.other.entity.RptOnlineCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RptOnlineCountMapper {
    long countByExample(RptOnlineCountExample example);

    int deleteByExample(RptOnlineCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RptOnlineCount record);

    int insertSelective(RptOnlineCount record);

    List<RptOnlineCount> selectByExample(RptOnlineCountExample example);

    RptOnlineCount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RptOnlineCount record, @Param("example") RptOnlineCountExample example);

    int updateByExample(@Param("record") RptOnlineCount record, @Param("example") RptOnlineCountExample example);

    int updateByPrimaryKeySelective(RptOnlineCount record);

    int updateByPrimaryKey(RptOnlineCount record);
}