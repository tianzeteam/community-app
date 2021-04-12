package com.smart.home.modules.community.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.smart.home.modules.community.dao.CommunityLikeHistoryMapper;
import com.smart.home.modules.community.entity.CommunityLikeHistory;
import com.smart.home.modules.community.entity.CommunityLikeHistoryExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Slf4j
@Service
public class CommunityLikeHistoryService {

    @Resource
    CommunityLikeHistoryMapper communityLikeHistoryMapper;
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityPostReplyService communityPostReplyService;

    public boolean create(CommunityLikeHistory communityLikeHistory) {
        boolean success = false;
        CommunityLikeHistory likeHistory = communityLikeHistoryMapper.selectByUserIdAndPostId(communityLikeHistory.getUserId(), communityLikeHistory.getPostId(), communityLikeHistory.getType());
        if (likeHistory != null) {
            log.warn("已经点赞过:{}", JSON.toJSONString(communityLikeHistory));
            return false;
        }

        communityLikeHistory.setCreatedTime(new Date());
        int affectRow = communityLikeHistoryMapper.insertSelective(communityLikeHistory);
        if (affectRow > 0) {
            if (communityLikeHistory.getType() == 0) {
                communityPostService.increaseLikeCount(communityLikeHistory.getPostId());
            }
            if (communityLikeHistory.getType() == 1) {
                communityPostReplyService.increaseLikeCount(communityLikeHistory.getPostId());
            }
            success = true;
        }
        return success;
    }

    public void unlikePost(Long userId, Long id) {
        CommunityLikeHistoryExample example = new CommunityLikeHistoryExample();
        example.createCriteria().andPostIdEqualTo(id).andUserIdEqualTo(userId).andTypeEqualTo(0);
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
        example.createCriteria().andPostIdEqualTo(id).andUserIdEqualTo(userId).andTypeEqualTo(1);
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
