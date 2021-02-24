package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductCommentStampHistoryMapper;
import com.smart.home.modules.product.entity.ProductCommentStampHistory;
import com.smart.home.modules.product.entity.ProductCommentStampHistoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 **/
@Service
public class ProductCommentStampHistoryService {

    @Resource
    ProductCommentStampHistoryMapper productCommentStampHistoryMapper;
    @Autowired
    private ProductCommentService productCommentService;
    @Autowired
    private ProductCommentReplyService productCommentReplyService;

    public int create(ProductCommentStampHistory productCommentStampHistory) {
        productCommentStampHistory.setCreatedTime(new Date());
        int affectRow = productCommentStampHistoryMapper.insertSelective(productCommentStampHistory);
        if (affectRow > 0) {
            if (productCommentStampHistory.getCategory() == 0) {
                productCommentService.increaseStampCount(productCommentStampHistory.getSourceId());
            }
            if (productCommentStampHistory.getCategory() == 1) {
                productCommentReplyService.increaseStampCount(productCommentStampHistory.getSourceId());
            }
        }
        return affectRow;
    }

    public void unstampComment(Long userId, Long id) {
        ProductCommentStampHistoryExample example = new ProductCommentStampHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andCategoryEqualTo(0);
        if (productCommentStampHistoryMapper.deleteByExample(example) > 0) {
            productCommentService.decreaseStampCount(id);
        }
    }

    public void unstampCommentReply(Long userId, Long id) {
        ProductCommentStampHistoryExample example = new ProductCommentStampHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andCategoryEqualTo(1);
        if (productCommentStampHistoryMapper.deleteByExample(example) > 0) {
            productCommentReplyService.decreaseStampCount(id);
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
