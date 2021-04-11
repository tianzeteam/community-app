package com.smart.home.modules.system.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.APIResponseCodeEnum;
import com.smart.home.modules.system.dao.SysRoleMapper;
import com.smart.home.modules.system.dao.SysRoleMenuMappingMapper;
import com.smart.home.modules.system.entity.*;
import com.smart.home.modules.user.dao.UserRoleMappingMapper;
import com.smart.home.modules.user.entity.UserRoleMapping;
import com.smart.home.modules.user.entity.UserRoleMappingExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/18
 **/
@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper mapper;
    @Resource
    private SysRoleMenuMappingMapper sysRoleMenuMappingMapper;
    @Resource
    private UserRoleMappingMapper userRoleMappingMapper;
    @Autowired
    private SysMenuService sysMenuService;

    public int insert(SysRole entity) {
        // key唯一的，检查重复性
        SysRoleExample example = new SysRoleExample();
        example.createCriteria().andCodeEqualTo(entity.getCode());
        if (mapper.countByExample(example) > 0) {
            throw new RuntimeException(APIResponseCodeEnum.ERROR_DUPLICATE_DATA.getMessage());
        }
        return mapper.insertSelective(entity);
    }

    public int update(SysRole entity) {
        // code不允许修改
        entity.setCode(null);
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(List<Long> idList) {
        for (Long id : idList) {
            mapper.deleteByPrimaryKey(id.intValue());
        }
        return 1;
    }

    public List<SysRole> selectByPage(SysRole entity, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SysRoleExample example = new SysRoleExample();
        SysRoleExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(entity.getName())) {
            criteria.andNameLike(entity.getName());
        }
        if (StringUtils.isNotBlank(entity.getRemark())) {
            criteria.andRemarkLike(entity.getRemark());
        }
        return mapper.selectByExample(example);
    }

    public String findRoleCodeById(Integer roleId) {
        return mapper.findRoleCodeById(roleId);
    }

    public Integer findRoleIdByCode(String roleCode) {
        return mapper.findRoleIdByCode(roleCode);
    }

    /**
     * 给角色授权菜单，先删除全部再重新授权
     * @param roleId
     * @param menuIdList
     */
    @Transactional(rollbackFor = Exception.class)
    public void assignMenus(Integer roleId, List<Integer> menuIdList) {
        SysRoleMenuMappingExample example = new SysRoleMenuMappingExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        sysRoleMenuMappingMapper.deleteByExample(example);
        String roleCode = findRoleCodeById(roleId);
        for (Integer menuId : menuIdList) {
            SysRoleMenuMapping sysRoleMenuMapping = new SysRoleMenuMapping();
            sysRoleMenuMapping.withRoleId(roleId).withRoleCode(roleCode).withMenuId(menuId);
            sysRoleMenuMappingMapper.insertSelective(sysRoleMenuMapping);
        }
    }

    /**
     * 查询角色下所有授权的菜单
     * @param roleIdList
     * @return
     */
    public List<SysMenu> queryAllMenus(List<Long> roleIdList) {
        List<Integer> menuIdList = sysRoleMenuMappingMapper.findMenuIdList(roleIdList);
        if (!CollectionUtils.isEmpty(menuIdList)) {
            return sysMenuService.findSysMenuByIdList(menuIdList);
        }
        return new ArrayList<>();
    }

    public void assignRole(String roleCode, Long userId) {
        // 判断有没有了，没有就新增
        Integer existing = userRoleMappingMapper.existsByRoleCodeAndUserId(roleCode, userId);
        if (Objects.isNull(existing)) {
            Integer roleId = findRoleIdByCode(roleCode);
            UserRoleMapping userRoleMapping = new UserRoleMapping();
            userRoleMapping.withRoleCode(roleCode).withRoleId(roleId).withUserId(userId);
            userRoleMappingMapper.insertSelective(userRoleMapping);
        }
    }

    public void removeRole(String roleCode, Long userId) {
        UserRoleMappingExample example = new UserRoleMappingExample();
        example.createCriteria().andUserIdEqualTo(userId).andRoleCodeEqualTo(roleCode);
        userRoleMappingMapper.deleteByExample(example);
    }
}
