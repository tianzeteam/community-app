package com.smart.home.modules.community.service;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.home.es.common.PageBean;
import com.smart.home.modules.community.dao.CommunityMapper;
import com.smart.home.modules.community.dao.CommunityUserMappingMapper;
import com.smart.home.modules.community.dto.CommunityUserMappingDTO;
import com.smart.home.modules.community.entity.Community;
import com.smart.home.modules.community.entity.CommunityExample;
import com.smart.home.modules.community.entity.CommunityUserMapping;
import com.smart.home.modules.community.entity.CommunityUserMappingExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author jason
 **/
@Service
public class CommunityUserMappingService {

    @Resource
    CommunityUserMappingMapper communityUserMappingMapper;
    @Resource
    private CommunityMapper communityMapper;


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

    public PageBean<List<CommunityUserMappingDTO>> selectByPage(CommunityUserMapping communityUserMapping, int pageNum, int pageSize) {
        CommunityUserMappingExample example = new CommunityUserMappingExample();
        CommunityUserMappingExample.Criteria criteria = example.createCriteria();
        if (communityUserMapping.getUserId() != null) {
            criteria.andUserIdEqualTo(communityUserMapping.getUserId());
        }
        List<CommunityUserMapping> communityUserMappings = communityUserMappingMapper.selectByExample(example);
        if (CollUtil.isEmpty(communityUserMappings)) {
            return new PageBean<>();
        }
        List<Integer> communityIds = communityUserMappings.parallelStream().map(CommunityUserMapping::getCommunityId).collect(Collectors.toList());
        CommunityExample communityExample = new CommunityExample();
        CommunityExample.Criteria cc = communityExample.createCriteria();
        cc.andIdIn(communityIds);
        PageHelper.startPage(pageNum, pageSize);
        List<Community> communities = communityMapper.selectByExample(communityExample);
        PageInfo pageInfo = new PageInfo(communities);
        List<CommunityUserMappingDTO> userMappingDTOS = new ArrayList<>();
        if (CollUtil.isNotEmpty(communities)) {
            communityUserMappings.stream().forEach(x->{
                communities.stream().forEach(y->{
                    if (x.getCommunityId().equals(y.getId())) {
                        CommunityUserMappingDTO userMappingDTO = new CommunityUserMappingDTO();
                        userMappingDTO.setId(x.getId());
                        userMappingDTO.setCommunityId(x.getCommunityId());
                        userMappingDTO.setCreatedTime(x.getCreatedTime());
                        userMappingDTO.setUserId(x.getUserId());
                        userMappingDTO.setCommunityTitle(y.getTitle());
                        userMappingDTO.setRemark(y.getRemark());
                        userMappingDTO.setSort(y.getSort());
                        userMappingDTO.setTopFlag(y.getTopFlag());
                        userMappingDTOS.add(userMappingDTO);
                    }
                });
            });
        }
        return PageBean.info2Bean(pageInfo, userMappingDTOS);
    }

    public CommunityUserMapping findById(Long id) {
        CommunityUserMapping communityUserMapping = communityUserMappingMapper.selectByPrimaryKey(id);
        return communityUserMapping;
    }

}
