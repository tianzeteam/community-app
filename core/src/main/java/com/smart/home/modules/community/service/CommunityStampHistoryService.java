package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.community.dao.CommunityStampHistoryMapper;
import com.smart.home.modules.community.entity.CommunityStampHistory;
import com.smart.home.modules.community.entity.CommunityStampHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class CommunityStampHistoryService {

    @Resource
    CommunityStampHistoryMapper communityStampHistoryMapper;

    public int create(CommunityStampHistory communityStampHistory) {
        communityStampHistory.setCreatedTime(new Date());
        return communityStampHistoryMapper.insertSelective(communityStampHistory);
    }

    public int update(CommunityStampHistory communityStampHistory) {
        return communityStampHistoryMapper.updateByPrimaryKeySelective(communityStampHistory);
    }

    public int deleteById(Long id) {
        return communityStampHistoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            communityStampHistoryMapper.deleteByPrimaryKey(id);
        }
    }

    public List<CommunityStampHistory> selectByPage(CommunityStampHistory communityStampHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CommunityStampHistoryExample example = new CommunityStampHistoryExample();
        CommunityStampHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return communityStampHistoryMapper.selectByExample(example);
    }

    public CommunityStampHistory findById(Long id) {
        CommunityStampHistory communityStampHistory = communityStampHistoryMapper.selectByPrimaryKey(id);
        return communityStampHistory;
    }

}
