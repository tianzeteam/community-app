package com.smart.home.modules.system.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.APIResponseCodeEnum;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.modules.system.dao.SysDictMapper;
import com.smart.home.modules.system.entity.SysDict;
import com.smart.home.modules.system.entity.SysDictExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/21
 **/
@Service
public class SysDictService {
    /**
     * 键值对
     */
    private static final int TYPE_KEY_VALUE = 0;
    /**
     * 树形结构
     */
    private static final int TYPE_TREE = 1;

    @Resource
    private SysDictMapper sysDictMapper;

    public int insert(SysDict sysDict) {
        SysDictExample example = new SysDictExample();
        example.createCriteria().andDictCodeEqualTo(sysDict.getDictCode());
        if (sysDictMapper.countByExample(example) > 0) {
            throw new RuntimeException(APIResponseCodeEnum.ERROR_DUPLICATE_DATA.getMessage());
        }
        // 分组没设置，设置为默认分组0
        if (Objects.isNull(sysDict.getDictGroup())) {
            sysDict.setDictGroup(0);
        }
        // 父主键ID没指定话默认顶级0
        if (Objects.isNull(sysDict.getPid())) {
            sysDict.setPid(0);
        }
        // 如果父主键ID大于0，说明是树形结构的
        if (sysDict.getPid() > 0) {
            sysDict.setDictType(TYPE_TREE);
        } else {
            sysDict.setDictType(TYPE_KEY_VALUE);
        }
        sysDict.setRevision(0);
        sysDict.setState(RecordStatusEnum.NORMAL.getStatus());
        sysDict.setCreatedTime(new Date());
        return sysDictMapper.insertSelective(sysDict);
    }

    public int update(SysDict sysDict) {
        sysDict.setDictCode(null);
        sysDict.setUpdatedTime(new Date());
        return sysDictMapper.updateByPrimaryKeySelective(sysDict);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(List<Long> idList) {
        for (Long id : idList) {
            sysDictMapper.deleteByPrimaryKey(id.intValue());
        }
        return 1;
    }

    public List<SysDict> selectByPage(SysDict sysDict, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SysDictExample example = new SysDictExample();
        SysDictExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sysDict.getDictCode())) {
            criteria.andDictCodeEqualTo(sysDict.getDictCode());
        }
        if (sysDict.getDictGroup() != null) {
            criteria.andDictGroupEqualTo(sysDict.getDictGroup());
        }
        if (StringUtils.isNotBlank(sysDict.getDictName())) {
            criteria.andDictNameLike(sysDict.getDictName());
        }
        if (sysDict.getDictType() != null) {
            criteria.andDictTypeEqualTo(sysDict.getDictType());
        }
        if (sysDict.getState() != null) {
            criteria.andStateEqualTo(sysDict.getState());
        }
        return sysDictMapper.selectByExample(example);
    }

    public SysDict queryByDictCode(String dictCode) {
        SysDictExample example = new SysDictExample();
        example.createCriteria().andDictCodeEqualTo(dictCode);
        List<SysDict> list = this.sysDictMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public SysDict findById(Integer id) {
        return sysDictMapper.selectByPrimaryKey(id);
    }
}
