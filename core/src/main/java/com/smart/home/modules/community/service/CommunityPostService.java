package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.modules.community.dao.CommunityPostMapper;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.entity.CommunityPostExample;
import com.smart.home.modules.user.service.UserAccountService;
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
public class CommunityPostService {

    @Resource
    CommunityPostMapper communityPostMapper;
    @Autowired
    private UserAccountService userAccountService;

    public int create(CommunityPost communityPost) {
        communityPost.setTopFlag(YesNoEnum.NO.getCode());
        communityPost.setBoutiqueFlag(YesNoEnum.NO.getCode());
        communityPost.setLikeCount(0);
        communityPost.setReportCount(0);
        communityPost.setAuditStatus(AuditStatusEnum.WAIT_AUDIT.getCode());
        communityPost.setRevision(0);
        communityPost.setStampCount(0);
        communityPost.setCollectCount(0);
        communityPost.setCreatedTime(new Date());
        return communityPostMapper.insertSelective(communityPost);
    }

    public int update(CommunityPost communityPost) {
        communityPost.setUpdatedTime(new Date());
        return communityPostMapper.updateByPrimaryKeySelective(communityPost);
    }

    public int deleteById(Long id) {
        return communityPostMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            // 软删除
            communityPostMapper.updateState(id, RecordStatusEnum.DELETE.getStatus());
        }
    }

    public List<CommunityPost> selectByPage(CommunityPost communityPost, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CommunityPostExample example = new CommunityPostExample();
        CommunityPostExample.Criteria criteria = example.createCriteria();
        if (communityPost.getUserId() != null) {
            criteria.andUserIdEqualTo(communityPost.getUserId());
        }
        example.setOrderByClause("created_time desc");
        List<CommunityPost> list = communityPostMapper.selectByExample(example);
        for (CommunityPost post : list) {
            post.setNickName(userAccountService.findNicknameByUserId(post.getUserId()));
        }
        return list;
    }

    public CommunityPost findById(Long id) {
        CommunityPost communityPost = communityPostMapper.selectByPrimaryKey(id);
        return communityPost;
    }

    public void increaseLikeCount(Long id) {
        communityPostMapper.increaseLikeCount(id);
    }

    public void decreaseLikeCount(Long id) {
        communityPostMapper.decreaseLikeCount(id);
    }

    public void increaseStampCount(Long id) {
        communityPostMapper.increaseStampCount(id);
    }

    public void decreaseStampCount(Long id) {
        communityPostMapper.decreaseStampCount(id);
    }

    public void increaseCollectCount(Long id) {
        communityPostMapper.increaseCollectCount(id);
    }

    public void decreaseCollectCount(Long id) {
        communityPostMapper.decreaseCollectCount(id);
    }

    public void updateBoutiqueFlag(Long postId, int flag) {
        communityPostMapper.updateBoutiqueFlag(postId, flag);
    }

    public void updateTopFlag(Long postId, int flag) {
        communityPostMapper.updateTopFlag(postId, flag);
    }

    public List<CommunityPost> queryViaUserIdByPage(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return communityPostMapper.queryViaUserIdByPage(userId);
    }
}
