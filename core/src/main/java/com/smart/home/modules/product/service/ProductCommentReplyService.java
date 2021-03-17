package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductCommentMapper;
import com.smart.home.modules.product.dao.ProductCommentReplyMapper;
import com.smart.home.modules.product.entity.ProductCommentReply;
import com.smart.home.modules.product.entity.ProductCommentReplyExample;
import com.smart.home.modules.user.entity.UserAccount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * @author jason
 **/
@Service
public class ProductCommentReplyService {

    @Resource
    ProductCommentReplyMapper productCommentReplyMapper;
    @Resource
    ProductCommentMapper productCommentMapper;

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
        UserAccount userAccount = productCommentReplyMapper.findUserAccountByCommentId(productCommentId);
        productCommentReply.setToUserId(userAccount.getId());
        productCommentReply.setToUserName(userAccount.getNickName());
        productCommentReplyMapper.insertSelective(productCommentReply);
        CompletableFuture.runAsync(()->{
            // 增加一个回复数量
            productCommentMapper.increaseReplyCount(productCommentId);
        });
    }

    public Long countReplyByDate(Date startDate, Date endDate) {
        ProductCommentReplyExample example  = new ProductCommentReplyExample();
        example.createCriteria().andCreatedTimeBetween(startDate, endDate);
        return productCommentReplyMapper.countByExample(example);
    }
}
