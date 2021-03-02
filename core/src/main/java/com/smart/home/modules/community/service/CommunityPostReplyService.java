package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.community.dao.CommunityPostReplyMapper;
import com.smart.home.modules.community.entity.CommunityPostReply;
import com.smart.home.modules.community.entity.CommunityPostReplyExample;
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
public class CommunityPostReplyService {

    @Resource
    CommunityPostReplyMapper communityPostReplyMapper;
    @Autowired
    private UserAccountService userAccountService;

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
        if (communityPostReply.getUserId() != null) {
            criteria.andUserIdEqualTo(communityPostReply.getUserId());
        }
        example.setOrderByClause("created_time desc");
        List<CommunityPostReply> list = communityPostReplyMapper.selectByExample(example);
        for (CommunityPostReply postReply : list) {
            postReply.setNickName(userAccountService.findNicknameByUserId(postReply.getUserId()));
        }
        return list;
    }

    public CommunityPostReply findById(Long id) {
        CommunityPostReply communityPostReply = communityPostReplyMapper.selectByPrimaryKey(id);
        return communityPostReply;
    }

    public void increaseLikeCount(Long id) {
        communityPostReplyMapper.increaseLikeCount(id);
    }

    public void decreaseLikeCount(Long id) {
        communityPostReplyMapper.decreaseLikeCount(id);
    }

    public void increaseStampCount(Long id) {
        communityPostReplyMapper.increaseStampCount(id);
    }

    public void decreaseStampCount(Long id) {
        communityPostReplyMapper.decreaseStampCount(id);
    }
}
