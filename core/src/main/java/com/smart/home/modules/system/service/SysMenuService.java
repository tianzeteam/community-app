package com.smart.home.modules.system.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.APIResponseCodeEnum;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.modules.system.dao.SysMenuMapper;
import com.smart.home.modules.system.entity.SysMenu;
import com.smart.home.modules.system.entity.SysMenuExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/20
 **/
@Service
public class SysMenuService {

    @Resource
    private SysMenuMapper mapper;

    public int insert(SysMenu entity) {
        SysMenuExample example = new SysMenuExample();
        example.createCriteria().andUrlEqualTo(entity.getUrl());
        if (mapper.countByExample(example) > 0) {
            throw new RuntimeException(APIResponseCodeEnum.ERROR_DUPLICATE_DATA.getMessage());
        }
        if (Objects.isNull(entity.getPid())) {
            entity.setPid(0);
        }
        if (Objects.isNull(entity.getSort())) {
            entity.setSort(0);
        }
        if (Objects.isNull(entity.getState())) {
            entity.setState(RecordStatusEnum.NORMAL.getStatus());
        }
        entity.setCreatedTime(new Date());
        return mapper.insertSelective(entity);
    }

    public int update(SysMenu entity) {
        if (Objects.isNull(entity.getPid())) {
            entity.setPid(0);
        }
        entity.setUpdatedTime(new Date());
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(List<Long> idList) {
        for (Long id : idList) {
            mapper.deleteByPrimaryKey(id.intValue());
        }
        return 1;
    }

    public List<SysMenu> selectByPage(SysMenu entity, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SysMenuExample example = new SysMenuExample();
        SysMenuExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(entity.getName())) {
            criteria.andNameLike(entity.getName());
        }
        if (StringUtils.isNotBlank(entity.getRemark())) {
            criteria.andRemarkLike(entity.getRemark());
        }
        if (entity.getType() != null) {
            criteria.andTypeEqualTo(entity.getType());
        }
        if (entity.getPid() != null) {
            criteria.andPidEqualTo(entity.getPid());
        }
        if (entity.getState() != null) {
            criteria.andStateEqualTo(entity.getState());
        }
        return mapper.selectByExample(example);
    }

    public List<SysMenu> findSysMenuByIdList(List<Integer> menuIdList) {
        SysMenuExample example = new SysMenuExample();
        example.createCriteria().andIdIn(menuIdList);
        return mapper.selectByExample(example);
    }
}
