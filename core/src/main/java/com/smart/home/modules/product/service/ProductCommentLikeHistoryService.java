package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductCommentLikeHistoryMapper;
import com.smart.home.modules.product.entity.ProductCommentLikeHistory;
import com.smart.home.modules.product.entity.ProductCommentLikeHistoryExample;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ProductCommentService productCommentService;
    @Autowired
    private ProductCommentReplyService productCommentReplyService;

    public int create(ProductCommentLikeHistory productCommentLikeHistory) {
        productCommentLikeHistory.setCreatedTime(new Date());
        int affectRow = productCommentLikeHistoryMapper.insertSelective(productCommentLikeHistory);
        if (affectRow > 0) {
            if (productCommentLikeHistory.getCategory() == 0) {
                productCommentService.increaseLikeCount(productCommentLikeHistory.getSourceId());
            }
            if (productCommentLikeHistory.getCategory() == 1) {
                productCommentReplyService.increaseLikeCount(productCommentLikeHistory.getSourceId());
            }
        }
        return affectRow;
    }

    public void unlikeComment(Long userId, Long id) {
        ProductCommentLikeHistoryExample example = new ProductCommentLikeHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andCategoryEqualTo(0);
        if (productCommentLikeHistoryMapper.deleteByExample(example) > 0) {
            productCommentService.decreaseLikeCount(id);
        }
    }

    public void unlikeCommentReply(Long userId, Long id) {
        ProductCommentLikeHistoryExample example = new ProductCommentLikeHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andCategoryEqualTo(1);
        if (productCommentLikeHistoryMapper.deleteByExample(example) > 0) {
            productCommentReplyService.decreaseLikeCount(id);
        }
    }

    public long countLike(Long userId, Long id, Integer category) {
        ProductCommentLikeHistoryExample example = new ProductCommentLikeHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andCategoryEqualTo(category);
        return productCommentLikeHistoryMapper.countByExample(example);
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
