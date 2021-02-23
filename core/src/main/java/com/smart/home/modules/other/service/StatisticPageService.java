package com.smart.home.modules.other.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.other.dao.StatisticPageMapper;
import com.smart.home.modules.other.entity.StatisticPage;
import com.smart.home.modules.other.entity.StatisticPageExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class StatisticPageService {

    @Resource
    StatisticPageMapper statisticPageMapper;

    public int create(StatisticPage statisticPage) {
        statisticPage.setCreatedTime(new Date());
        return statisticPageMapper.insertSelective(statisticPage);
    }

    public int update(StatisticPage statisticPage) {
        statisticPage.setUpdatedTime(new Date());
        return statisticPageMapper.updateByPrimaryKeySelective(statisticPage);
    }

    public int deleteById(Long id) {
        return statisticPageMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            statisticPageMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<StatisticPage> selectByPage(StatisticPage statisticPage, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        StatisticPageExample example = new StatisticPageExample();
        StatisticPageExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return statisticPageMapper.selectByExample(example);
    }

    public StatisticPage findById(Long id) {
        StatisticPage statisticPage = statisticPageMapper.selectByPrimaryKey(id.intValue());
        return statisticPage;
    }

}
