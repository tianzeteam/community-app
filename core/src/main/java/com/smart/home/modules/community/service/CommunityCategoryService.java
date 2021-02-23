package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.community.dao.CommunityCategoryMapper;
import com.smart.home.modules.community.entity.CommunityCategory;
import com.smart.home.modules.community.entity.CommunityCategoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class CommunityCategoryService {

    @Resource
    CommunityCategoryMapper communityCategoryMapper;

    public int create(CommunityCategory communityCategory) {
        communityCategory.setCreatedTime(new Date());
        return communityCategoryMapper.insertSelective(communityCategory);
    }

    public int update(CommunityCategory communityCategory) {
        communityCategory.setUpdatedTime(new Date());
        return communityCategoryMapper.updateByPrimaryKeySelective(communityCategory);
    }

    public int deleteById(Long id) {
        return communityCategoryMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            communityCategoryMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<CommunityCategory> selectByPage(CommunityCategory communityCategory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CommunityCategoryExample example = new CommunityCategoryExample();
        CommunityCategoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return communityCategoryMapper.selectByExample(example);
    }

    public CommunityCategory findById(Long id) {
        CommunityCategory communityCategory = communityCategoryMapper.selectByPrimaryKey(id.intValue());
        return communityCategory;
    }

}
