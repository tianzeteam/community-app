package com.smart.home.modules.message.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.message.dao.MessageReadHistoryMapper;
import com.smart.home.modules.message.entity.MessageReadHistory;
import com.smart.home.modules.message.entity.MessageReadHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class MessageReadHistoryService {

    @Resource
    MessageReadHistoryMapper messageReadHistoryMapper;

    public int create(MessageReadHistory messageReadHistory) {
        messageReadHistory.setCreatedTime(new Date());
        return messageReadHistoryMapper.insertSelective(messageReadHistory);
    }

    public int update(MessageReadHistory messageReadHistory) {
        return messageReadHistoryMapper.updateByPrimaryKeySelective(messageReadHistory);
    }

    public int deleteById(Long id) {
        return messageReadHistoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            messageReadHistoryMapper.deleteByPrimaryKey(id);
        }
    }

    public List<MessageReadHistory> selectByPage(MessageReadHistory messageReadHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        MessageReadHistoryExample example = new MessageReadHistoryExample();
        MessageReadHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return messageReadHistoryMapper.selectByExample(example);
    }

    public MessageReadHistory findById(Long id) {
        MessageReadHistory messageReadHistory = messageReadHistoryMapper.selectByPrimaryKey(id);
        return messageReadHistory;
    }

}
