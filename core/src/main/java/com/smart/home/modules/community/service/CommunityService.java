package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.community.dao.CommunityMapper;
import com.smart.home.modules.community.entity.Community;
import com.smart.home.modules.community.entity.CommunityExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class CommunityService {

    @Resource
    CommunityMapper communityMapper;

    public int create(Community community) {
        community.setCreatedTime(new Date());
        return communityMapper.insertSelective(community);
    }

    public int update(Community community) {
        community.setUpdatedTime(new Date());
        return communityMapper.updateByPrimaryKeySelective(community);
    }

    public int deleteById(Long id) {
        return communityMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            communityMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<Community> selectByPage(Community community, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CommunityExample example = new CommunityExample();
        CommunityExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return communityMapper.selectByExample(example);
    }

    public Community findById(Long id) {
        Community community = communityMapper.selectByPrimaryKey(id.intValue());
        return community;
    }

}
