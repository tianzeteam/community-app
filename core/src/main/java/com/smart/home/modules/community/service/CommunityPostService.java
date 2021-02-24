package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.community.dao.CommunityPostMapper;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.entity.CommunityPostExample;
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

    public int create(CommunityPost communityPost) {
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
            communityPostMapper.deleteByPrimaryKey(id);
        }
    }

    public List<CommunityPost> selectByPage(CommunityPost communityPost, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CommunityPostExample example = new CommunityPostExample();
        CommunityPostExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return communityPostMapper.selectByExample(example);
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
}
