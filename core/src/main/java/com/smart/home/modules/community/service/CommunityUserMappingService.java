package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.community.dao.CommunityUserMappingMapper;
import com.smart.home.modules.community.entity.CommunityUserMapping;
import com.smart.home.modules.community.entity.CommunityUserMappingExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class CommunityUserMappingService {

    @Resource
    CommunityUserMappingMapper communityUserMappingMapper;

    public int create(CommunityUserMapping communityUserMapping) {
        communityUserMapping.setCreatedTime(new Date());
        return communityUserMappingMapper.insertSelective(communityUserMapping);
    }

    public int update(CommunityUserMapping communityUserMapping) {
        return communityUserMappingMapper.updateByPrimaryKeySelective(communityUserMapping);
    }

    public int deleteById(Long id) {
        return communityUserMappingMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            communityUserMappingMapper.deleteByPrimaryKey(id);
        }
    }

    public List<CommunityUserMapping> selectByPage(CommunityUserMapping communityUserMapping, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CommunityUserMappingExample example = new CommunityUserMappingExample();
        CommunityUserMappingExample.Criteria criteria = example.createCriteria();
        if (communityUserMapping.getUserId() != null) {
            criteria.andUserIdEqualTo(communityUserMapping.getUserId());
        }
        return communityUserMappingMapper.selectByExample(example);
    }

    public CommunityUserMapping findById(Long id) {
        CommunityUserMapping communityUserMapping = communityUserMappingMapper.selectByPrimaryKey(id);
        return communityUserMapping;
    }

}
