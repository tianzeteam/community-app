package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.community.dao.CommunityLikeHistoryMapper;
import com.smart.home.modules.community.entity.CommunityLikeHistory;
import com.smart.home.modules.community.entity.CommunityLikeHistoryExample;
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
public class CommunityLikeHistoryService {

    @Resource
    CommunityLikeHistoryMapper communityLikeHistoryMapper;
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityPostReplyService communityPostReplyService;

    public int create(CommunityLikeHistory communityLikeHistory) {
        communityLikeHistory.setCreatedTime(new Date());
        int affectRow = communityLikeHistoryMapper.insertSelective(communityLikeHistory);
        if (affectRow > 0) {
            if (communityLikeHistory.getType() == 0) {
                communityPostService.increaseLikeCount(communityLikeHistory.getPostId());
            }
            if (communityLikeHistory.getType() == 1) {
                communityPostReplyService.increaseLikeCount(communityLikeHistory.getPostId());
            }
        }
        return affectRow;
    }

    public void unlikePost(Long userId, Long id) {
        CommunityLikeHistoryExample example = new CommunityLikeHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andTypeEqualTo(0);
        if (communityLikeHistoryMapper.deleteByExample(example) > 0) {
            communityPostService.decreaseLikeCount(id);
        }
    }

    public long countLike(Long userId, Long id, Integer category) {
        CommunityLikeHistoryExample example = new CommunityLikeHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andTypeEqualTo(category);
        return communityLikeHistoryMapper.countByExample(example);
    }

    public void unlikePostReply(Long userId, Long id) {
        CommunityLikeHistoryExample example = new CommunityLikeHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andTypeEqualTo(1);
        if (communityLikeHistoryMapper.deleteByExample(example) > 0) {
            communityPostReplyService.decreaseLikeCount(id);
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
