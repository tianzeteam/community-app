package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.community.dao.CommunityLikeHistoryMapper;
import com.smart.home.modules.community.entity.CommunityLikeHistory;
import com.smart.home.modules.community.entity.CommunityLikeHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class CommunityLikeHistoryService {

    @Resource
    CommunityLikeHistoryMapper communityLikeHistoryMapper;

    public int create(CommunityLikeHistory communityLikeHistory) {
        communityLikeHistory.setCreatedTime(new Date());
        return communityLikeHistoryMapper.insertSelective(communityLikeHistory);
    }

    public int update(CommunityLikeHistory communityLikeHistory) {
        return communityLikeHistoryMapper.updateByPrimaryKeySelective(communityLikeHistory);
    }

    public int deleteById(Long id) {
        return communityLikeHistoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            communityLikeHistoryMapper.deleteByPrimaryKey(id);
        }
    }

    public List<CommunityLikeHistory> selectByPage(CommunityLikeHistory communityLikeHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CommunityLikeHistoryExample example = new CommunityLikeHistoryExample();
        CommunityLikeHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return communityLikeHistoryMapper.selectByExample(example);
    }

    public CommunityLikeHistory findById(Long id) {
        CommunityLikeHistory communityLikeHistory = communityLikeHistoryMapper.selectByPrimaryKey(id);
        return communityLikeHistory;
    }

}
