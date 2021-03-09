package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.modules.community.dao.CommunityCategoryMapper;
import com.smart.home.modules.community.dao.CommunityMapper;
import com.smart.home.modules.community.entity.CommunityCategory;
import com.smart.home.modules.community.entity.CommunityCategoryExample;
import org.apache.commons.lang3.StringUtils;
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
    @Resource
    CommunityMapper communityMapper;

    public int create(CommunityCategory communityCategory) throws ServiceException {
        // 检查同名
        CommunityCategoryExample example = new CommunityCategoryExample();
        example.createCriteria().andTitleEqualTo(communityCategory.getTitle());
        if (communityCategoryMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该社区类目已经存在");
        }
        communityCategory.setCreatedTime(new Date());
        communityCategory.setRevision(0);
        communityCategory.setState(RecordStatusEnum.NORMAL.getStatus());
        return communityCategoryMapper.insertSelective(communityCategory);
    }

    public int update(CommunityCategory communityCategory) throws ServiceException {
        // 检查同名
        CommunityCategoryExample example = new CommunityCategoryExample();
        example.createCriteria().andTitleEqualTo(communityCategory.getTitle()).andIdNotEqualTo(communityCategory.getId());
        if (communityCategoryMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该社区类目已经存在");
        }
        communityCategory.setUpdatedTime(new Date());
        return communityCategoryMapper.updateByPrimaryKeySelective(communityCategory);
    }

    public int deleteById(Long id) {
        return communityCategoryMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) throws ServiceException {
        for (Long id : idList) {
            // 检查有没有关联到社区了
            if (communityMapper.countByCategoryId(id) > 0) {
                throw new ServiceException("社区类目已经关联到社区了，不能删除");
            }
            communityCategoryMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<CommunityCategory> selectByPage(CommunityCategory communityCategory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CommunityCategoryExample example = new CommunityCategoryExample();
        CommunityCategoryExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(communityCategory.getTitle())) {
            criteria.andTitleEqualTo(communityCategory.getTitle());
        }
        example.setOrderByClause("sort desc");
        return communityCategoryMapper.selectByExample(example);
    }

    public CommunityCategory findById(Long id) {
        CommunityCategory communityCategory = communityCategoryMapper.selectByPrimaryKey(id.intValue());
        return communityCategory;
    }

    public List<CommunityCategory> queryAllValid() {
        CommunityCategoryExample example = new CommunityCategoryExample();
        example.createCriteria().andStateEqualTo(RecordStatusEnum.NORMAL.getStatus());
        example.setOrderByClause("sort desc");
        return communityCategoryMapper.selectByExample(example);
    }
}
