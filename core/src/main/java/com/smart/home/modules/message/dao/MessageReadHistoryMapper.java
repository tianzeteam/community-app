package com.smart.home.modules.message.dao;

import com.smart.home.modules.message.entity.MessageReadHistory;
import com.smart.home.modules.message.entity.MessageReadHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MessageReadHistoryMapper {
    long countByExample(MessageReadHistoryExample example);

    int deleteByExample(MessageReadHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MessageReadHistory record);

    int insertSelective(MessageReadHistory record);

    List<MessageReadHistory> selectByExample(MessageReadHistoryExample example);

    MessageReadHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MessageReadHistory record, @Param("example") MessageReadHistoryExample example);

    int updateByExample(@Param("record") MessageReadHistory record, @Param("example") MessageReadHistoryExample example);

    int updateByPrimaryKeySelective(MessageReadHistory record);

    int updateByPrimaryKey(MessageReadHistory record);
}