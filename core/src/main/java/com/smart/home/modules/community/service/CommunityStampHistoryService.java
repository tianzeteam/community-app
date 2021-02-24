package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.community.dao.CommunityStampHistoryMapper;
import com.smart.home.modules.community.entity.CommunityStampHistory;
import com.smart.home.modules.community.entity.CommunityStampHistoryExample;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityPostReplyService communityPostReplyService;

    public int create(CommunityStampHistory communityStampHistory) {
        communityStampHistory.setCreatedTime(new Date());
        int affectRow = communityStampHistoryMapper.insertSelective(communityStampHistory);
        if (affectRow > 0) {
            if (communityStampHistory.getType() == 0) {
                communityPostService.increaseStampCount(communityStampHistory.getPostId());
            }
            if (communityStampHistory.getType() == 1) {
                communityPostReplyService.increaseStampCount(communityStampHistory.getPostId());
            }
        }
        return affectRow;
    }

    public void unstampPost(Long userId, Long id) {
        CommunityStampHistoryExample example = new CommunityStampHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andTypeEqualTo(0);
        if (communityStampHistoryMapper.deleteByExample(example) > 0) {
            communityPostService.decreaseStampCount(id);
        }
    }

    public void unstampPostReply(Long userId, Long id) {
        CommunityStampHistoryExample example = new CommunityStampHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andTypeEqualTo(1);
        if (communityStampHistoryMapper.deleteByExample(example) > 0) {
            communityPostReplyService.decreaseStampCount(id);
        }
    }

    public long countStamp(Long userId, Long id, int category) {
        CommunityStampHistoryExample example = new CommunityStampHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andTypeEqualTo(category);
        return communityStampHistoryMapper.countByExample(example);
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
