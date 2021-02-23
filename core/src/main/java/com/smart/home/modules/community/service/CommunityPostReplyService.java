package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.community.dao.CommunityPostReplyMapper;
import com.smart.home.modules.community.entity.CommunityPostReply;
import com.smart.home.modules.community.entity.CommunityPostReplyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class CommunityPostReplyService {

    @Resource
    CommunityPostReplyMapper communityPostReplyMapper;

    public int create(CommunityPostReply communityPostReply) {
        communityPostReply.setCreatedTime(new Date());
        return communityPostReplyMapper.insertSelective(communityPostReply);
    }

    public int update(CommunityPostReply communityPostReply) {
        return communityPostReplyMapper.updateByPrimaryKeySelective(communityPostReply);
    }

    public int deleteById(Long id) {
        return communityPostReplyMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            communityPostReplyMapper.deleteByPrimaryKey(id);
        }
    }

    public List<CommunityPostReply> selectByPage(CommunityPostReply communityPostReply, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CommunityPostReplyExample example = new CommunityPostReplyExample();
        CommunityPostReplyExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return communityPostReplyMapper.selectByExample(example);
    }

    public CommunityPostReply findById(Long id) {
        CommunityPostReply communityPostReply = communityPostReplyMapper.selectByPrimaryKey(id);
        return communityPostReply;
    }

}
