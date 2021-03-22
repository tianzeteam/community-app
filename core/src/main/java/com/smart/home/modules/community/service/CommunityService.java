package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.FileUtils;
import com.smart.home.modules.community.dao.CommunityCategoryMapper;
import com.smart.home.modules.community.dao.CommunityMapper;
import com.smart.home.modules.community.dao.CommunityPostMapper;
import com.smart.home.modules.community.entity.Community;
import com.smart.home.modules.community.entity.CommunityCategory;
import com.smart.home.modules.community.entity.CommunityExample;
import com.smart.home.modules.system.service.SysFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;
import java.util.Objects;

/**
 * @author jason
 **/
@Service
public class CommunityService {

    @Resource
    CommunityMapper communityMapper;
    @Resource
    CommunityCategoryMapper communityCategoryMapper;
    @Resource
    CommunityPostMapper communityPostMapper;
    @Resource
    private SysFileService sysFileService;

    public int create(Community community) throws ServiceException {
        CommunityCategory communityCategory = communityCategoryMapper.selectByPrimaryKey(community.getCategoryId().intValue());
        if (Objects.isNull(communityCategory)) {
            throw new ServiceException("社区类目不存在");
        }
        CommunityExample example = new CommunityExample();
        example.createCriteria().andTitleEqualTo(community.getTitle());
        if (communityMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该社区已经存在");
        }
        community.setCategoryName(communityCategory.getTitle());
        community.setFollowerCount(0);
        community.setRevision(0);
        community.setCreatedTime(new Date());
        int affectRow = communityMapper.insertSelective(community);
        if (StringUtils.isNotBlank(community.getCoverImage())) {
            sysFileService.sync(FileUtils.getFileNameFromUrl(community.getCoverImage()));
        }
        return affectRow;
    }

    public int update(Community community) throws ServiceException {
        CommunityCategory communityCategory = communityCategoryMapper.selectByPrimaryKey(community.getCategoryId().intValue());
        if (Objects.isNull(communityCategory)) {
            throw new ServiceException("社区类目不存在");
        }
        CommunityExample example = new CommunityExample();
        example.createCriteria().andTitleEqualTo(community.getTitle()).andIdNotEqualTo(community.getId());
        if (communityMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该社区已经存在");
        }
        community.setUpdatedTime(new Date());
        return communityMapper.updateByPrimaryKeySelective(community);
    }

    public int deleteById(Long id) {
        return communityMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) throws ServiceException {
        for (Long id : idList) {
            // 检查有没有关联的帖子
            if (communityPostMapper.countByCommunityId(id.intValue()) > 0) {
                throw new ServiceException("已经有关联的帖子了，不能删除");
            }
            communityMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<Community> selectByPage(Community community, int pageNum, int pageSize) {
        CommunityExample example = new CommunityExample();
        CommunityExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(community.getTitle())) {
            criteria.andTitleEqualTo(community.getTitle());
        }
        if (community.getCategoryId() != null) {
            criteria.andCategoryIdEqualTo(community.getCategoryId());
        }
        example.setOrderByClause("sort desc");
        PageHelper.startPage(pageNum, pageSize);
        return communityMapper.selectByExample(example);
    }

    public List<Community> selectAll(){
        CommunityExample example = new CommunityExample();
        CommunityExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort desc");
        return communityMapper.selectByExample(example);
    }

    public Community findById(Long id) {
        Community community = communityMapper.selectByPrimaryKey(id.intValue());
        return community;
    }

    public List<Community> queryAllValid() {
        CommunityExample example = new CommunityExample();
        example.setOrderByClause("sort desc");
        return communityMapper.selectByExample(example);
    }

}
