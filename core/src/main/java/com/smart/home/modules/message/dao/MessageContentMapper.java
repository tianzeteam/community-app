package com.smart.home.modules.message.dao;

import com.smart.home.modules.message.dto.UnReadMessageSummary;
import com.smart.home.modules.message.entity.MessageContent;
import com.smart.home.modules.message.entity.MessageContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MessageContentMapper {
    long countByExample(MessageContentExample example);

    int deleteByExample(MessageContentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MessageContent record);

    int insertSelective(MessageContent record);

    List<MessageContent> selectByExample(MessageContentExample example);

    MessageContent selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MessageContent record, @Param("example") MessageContentExample example);

    int updateByExample(@Param("record") MessageContent record, @Param("example") MessageContentExample example);

    int updateByPrimaryKeySelective(MessageContent record);

    int updateByPrimaryKey(MessageContent record);

    List<MessageContent> queryUnReadNotifyMessage(@Param("receiverId") Long receiverId);

    int updateToRead(@Param("messageIdList") List<Long> messageIdList,@Param("readFlag") int readFlag, @Param("toUserId") Long toUserId);

    List<MessageContent> queryChatMessage(@Param("loginUserId") Long loginUserId,@Param("fromUserId") Long fromUserId);

    List<UnReadMessageSummary> queryUnReadMessageSummary(@Param("receiverId") Long receiverId);
}