package com.smart.home.modules.message.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.enums.MessageTypeEnum;
import com.smart.home.modules.message.dao.MessageContentMapper;
import com.smart.home.modules.message.entity.MessageContent;
import com.smart.home.modules.message.entity.MessageContentExample;
import com.smart.home.modules.message.entity.MessageReadHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class MessageContentService {

    @Resource
    MessageContentMapper messageContentMapper;
    @Resource
    MessageReadHistoryService messageReadHistoryService;

    public int createNotifyMessage(MessageContent messageContent) {
        messageContent.setReadFlag(YesNoEnum.NO.getCode());
        messageContent.setCreatedTime(new Date());
        messageContent.setDeleteFlag(YesNoEnum.NO.getCode());
        messageContent.setMessageType(MessageTypeEnum.NOTIFY.getType());
        return this.messageContentMapper.insertSelective(messageContent);
    }

    public int update(MessageContent messageContent) {
        return messageContentMapper.updateByPrimaryKeySelective(messageContent);
    }

    public int deleteById(Long id) {
        return messageContentMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            messageContentMapper.deleteByPrimaryKey(id);
        }
    }

    public List<MessageContent> selectByPage(MessageContent messageContent, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        MessageContentExample example = new MessageContentExample();
        MessageContentExample.Criteria criteria = example.createCriteria();
        if (messageContent.getMessageType() != null) {
            criteria.andMessageTypeEqualTo(messageContent.getMessageType());
        }
        return messageContentMapper.selectByExample(example);
    }

    public MessageContent findById(Long id) {
        MessageContent messageContent = messageContentMapper.selectByPrimaryKey(id);
        return messageContent;
    }

    public List<MessageContent> queryMessageByPage(MessageTypeEnum messageTypeEnum, Long receiverId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        switch (messageTypeEnum) {
            case NOTIFY:
                return messageContentMapper.queryUnReadNotifyMessage(receiverId);
            case LIKE:
            case REPLY_ME:
                MessageContentExample example = new MessageContentExample();
                MessageContentExample.Criteria criteria = example.createCriteria();
                if (receiverId != null) {
                    criteria.andReceiverIdEqualTo(receiverId);
                }
                criteria.andMessageTypeEqualTo(messageTypeEnum.getType());
                example.setOrderByClause("created_time desc");
                return messageContentMapper.selectByExample(example);
            default:
                return null;
        }
    }

    /**
     * 标记已读
     * @param idList
     * @param loginUserId
     */
    public void markRead(List<Long> idList, Long loginUserId) {
        for (Long id : idList) {
            MessageReadHistory messageReadHistory = new MessageReadHistory();
            messageReadHistory.withCreatedTime(new Date())
                    .withMessageId(id)
                    .withUserId(loginUserId);
            messageReadHistoryService.create(messageReadHistory);
        }
    }

    public void create(MessageContent messageContent) {
        messageContentMapper.insertSelective(messageContent);
    }
}
