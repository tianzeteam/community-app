package com.smart.home.modules.message.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.message.dao.MessageContentMapper;
import com.smart.home.modules.message.entity.MessageContent;
import com.smart.home.modules.message.entity.MessageContentExample;
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

    public int create(MessageContent messageContent) {
        messageContent.setCreatedTime(new Date());
        return messageContentMapper.insertSelective(messageContent);
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
        // TODO 按需根据字段查询
        return messageContentMapper.selectByExample(example);
    }

    public MessageContent findById(Long id) {
        MessageContent messageContent = messageContentMapper.selectByPrimaryKey(id);
        return messageContent;
    }

}
