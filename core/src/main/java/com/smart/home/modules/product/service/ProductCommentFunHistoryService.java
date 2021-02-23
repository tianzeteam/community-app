package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductCommentFunHistoryMapper;
import com.smart.home.modules.product.entity.ProductCommentFunHistory;
import com.smart.home.modules.product.entity.ProductCommentFunHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductCommentFunHistoryService {

    @Resource
    ProductCommentFunHistoryMapper productCommentFunHistoryMapper;

    public int create(ProductCommentFunHistory productCommentFunHistory) {
        productCommentFunHistory.setCreatedTime(new Date());
        return productCommentFunHistoryMapper.insertSelective(productCommentFunHistory);
    }

    public int update(ProductCommentFunHistory productCommentFunHistory) {
        return productCommentFunHistoryMapper.updateByPrimaryKeySelective(productCommentFunHistory);
    }

    public int deleteById(Long id) {
        return productCommentFunHistoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productCommentFunHistoryMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ProductCommentFunHistory> selectByPage(ProductCommentFunHistory productCommentFunHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductCommentFunHistoryExample example = new ProductCommentFunHistoryExample();
        ProductCommentFunHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productCommentFunHistoryMapper.selectByExample(example);
    }

    public ProductCommentFunHistory findById(Long id) {
        ProductCommentFunHistory productCommentFunHistory = productCommentFunHistoryMapper.selectByPrimaryKey(id);
        return productCommentFunHistory;
    }

}
