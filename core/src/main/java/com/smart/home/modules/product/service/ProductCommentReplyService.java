package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductCommentReplyMapper;
import com.smart.home.modules.product.entity.ProductCommentReply;
import com.smart.home.modules.product.entity.ProductCommentReplyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductCommentReplyService {

    @Resource
    ProductCommentReplyMapper productCommentReplyMapper;

    public int create(ProductCommentReply productCommentReply) {
        productCommentReply.setCreatedTime(new Date());
        return productCommentReplyMapper.insertSelective(productCommentReply);
    }

    public int update(ProductCommentReply productCommentReply) {
        return productCommentReplyMapper.updateByPrimaryKeySelective(productCommentReply);
    }

    public int deleteById(Long id) {
        return productCommentReplyMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productCommentReplyMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ProductCommentReply> selectByPage(ProductCommentReply productCommentReply, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductCommentReplyExample example = new ProductCommentReplyExample();
        ProductCommentReplyExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productCommentReplyMapper.selectByExample(example);
    }

    public ProductCommentReply findById(Long id) {
        ProductCommentReply productCommentReply = productCommentReplyMapper.selectByPrimaryKey(id);
        return productCommentReply;
    }

    public void increaseLikeCount(Long id) {
        productCommentReplyMapper.increaseLikeCount(id);
    }

    public void decreaseLikeCount(Long id) {
        productCommentReplyMapper.decreaseLikeCount(id);
    }

    public void increaseStampCount(Long id) {
        productCommentReplyMapper.increaseStampCount(id);
    }

    public void decreaseStampCount(Long id) {
        productCommentReplyMapper.decreaseStampCount(id);
    }

    public void increaseFunCount(Long id) {
        productCommentReplyMapper.increaseFunCount(id);
    }

    public void decreaseFunCount(Long id) {
        productCommentReplyMapper.decreaseFunCount(id);
    }

    public List<ProductCommentReply> queryCommentDetailReplyByPage(Long userId, Long productCommentId, Long pid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return productCommentReplyMapper.queryCommentDetailReplyByPage(userId, productCommentId, pid);
    }

    public void create(Long userId, String details, Long productCommentId, Long pid) {
        ProductCommentReply productCommentReply = new ProductCommentReply();
        productCommentReply.withCommentId(productCommentId)
                .withCreatedTime(new Date())
                .withDetails(details)
                .withLikeCount(0)
                .withPid(pid)
                .withRevision(0)
                .withStampCount(0)
                .withUserId(userId);
        productCommentReplyMapper.insertSelective(productCommentReply);
    }

    public Long countReplyByDate(Date startDate, Date endDate) {
        ProductCommentReplyExample example  = new ProductCommentReplyExample();
        example.createCriteria().andCreatedTimeBetween(startDate, endDate);
        return productCommentReplyMapper.countByExample(example);
    }
}
