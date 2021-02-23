package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductCommentStampHistoryMapper;
import com.smart.home.modules.product.entity.ProductCommentStampHistory;
import com.smart.home.modules.product.entity.ProductCommentStampHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductCommentStampHistoryService {

    @Resource
    ProductCommentStampHistoryMapper productCommentStampHistoryMapper;

    public int create(ProductCommentStampHistory productCommentStampHistory) {
        productCommentStampHistory.setCreatedTime(new Date());
        return productCommentStampHistoryMapper.insertSelective(productCommentStampHistory);
    }

    public int update(ProductCommentStampHistory productCommentStampHistory) {
        return productCommentStampHistoryMapper.updateByPrimaryKeySelective(productCommentStampHistory);
    }

    public int deleteById(Long id) {
        return productCommentStampHistoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productCommentStampHistoryMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ProductCommentStampHistory> selectByPage(ProductCommentStampHistory productCommentStampHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductCommentStampHistoryExample example = new ProductCommentStampHistoryExample();
        ProductCommentStampHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productCommentStampHistoryMapper.selectByExample(example);
    }

    public ProductCommentStampHistory findById(Long id) {
        ProductCommentStampHistory productCommentStampHistory = productCommentStampHistoryMapper.selectByPrimaryKey(id);
        return productCommentStampHistory;
    }

}
