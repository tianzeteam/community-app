package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductCommentFunHistoryMapper;
import com.smart.home.modules.product.entity.ProductCommentFunHistory;
import com.smart.home.modules.product.entity.ProductCommentFunHistoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 **/
@Service
public class ProductCommentFunHistoryService {

    @Resource
    ProductCommentFunHistoryMapper productCommentFunHistoryMapper;
    @Autowired
    private ProductCommentService productCommentService;
    @Autowired
    private ProductCommentReplyService productCommentReplyService;

    public int create(ProductCommentFunHistory productCommentFunHistory) {
        productCommentFunHistory.setCreatedTime(new Date());
        int affectRow = productCommentFunHistoryMapper.insertSelective(productCommentFunHistory);
        if (affectRow > 0) {
            if (productCommentFunHistory.getCategory() == 0) {
                productCommentService.increaseFunCount(productCommentFunHistory.getSourceId());
            }
            if (productCommentFunHistory.getCategory() == 1) {
                productCommentReplyService.increaseFunCount(productCommentFunHistory.getSourceId());
            }
        }
        return affectRow;
    }

    public void unfunCommnet(Long userId, Long id) {
        ProductCommentFunHistoryExample example = new ProductCommentFunHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andCategoryEqualTo(0);
        if (productCommentFunHistoryMapper.deleteByExample(example) > 0) {
            productCommentService.decreaseFunCount(id);
        }
    }

    public void unfunCommnetReply(Long userId, Long id) {
        ProductCommentFunHistoryExample example = new ProductCommentFunHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andCategoryEqualTo(1);
        if (productCommentFunHistoryMapper.deleteByExample(example) > 0) {
            productCommentReplyService.decreaseFunCount(id);
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
