package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductCommentLikeHistoryMapper;
import com.smart.home.modules.product.entity.ProductCommentLikeHistory;
import com.smart.home.modules.product.entity.ProductCommentLikeHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductCommentLikeHistoryService {

    @Resource
    ProductCommentLikeHistoryMapper productCommentLikeHistoryMapper;

    public int create(ProductCommentLikeHistory productCommentLikeHistory) {
        productCommentLikeHistory.setCreatedTime(new Date());
        return productCommentLikeHistoryMapper.insertSelective(productCommentLikeHistory);
    }

    public int update(ProductCommentLikeHistory productCommentLikeHistory) {
        return productCommentLikeHistoryMapper.updateByPrimaryKeySelective(productCommentLikeHistory);
    }

    public int deleteById(Long id) {
        return productCommentLikeHistoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productCommentLikeHistoryMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ProductCommentLikeHistory> selectByPage(ProductCommentLikeHistory productCommentLikeHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductCommentLikeHistoryExample example = new ProductCommentLikeHistoryExample();
        ProductCommentLikeHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productCommentLikeHistoryMapper.selectByExample(example);
    }

    public ProductCommentLikeHistory findById(Long id) {
        ProductCommentLikeHistory productCommentLikeHistory = productCommentLikeHistoryMapper.selectByPrimaryKey(id);
        return productCommentLikeHistory;
    }

}
