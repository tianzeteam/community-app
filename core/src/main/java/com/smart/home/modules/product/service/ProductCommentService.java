package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductCommentMapper;
import com.smart.home.modules.product.entity.ProductComment;
import com.smart.home.modules.product.entity.ProductCommentExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 **/
@Service
public class ProductCommentService {

    @Resource
    ProductCommentMapper productCommentMapper;

    public int create(ProductComment productComment) {
        productComment.setCreatedTime(new Date());
        return productCommentMapper.insertSelective(productComment);
    }

    public int update(ProductComment productComment) {
        return productCommentMapper.updateByPrimaryKeySelective(productComment);
    }

    public int deleteById(Long id) {
        return productCommentMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productCommentMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ProductComment> selectByPage(ProductComment productComment, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductCommentExample example = new ProductCommentExample();
        ProductCommentExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return productCommentMapper.selectByExample(example);
    }

    public ProductComment findById(Long id) {
        ProductComment productComment = productCommentMapper.selectByPrimaryKey(id);
        return productComment;
    }

    public void increaseLikeCount(Long id) {
        productCommentMapper.increaseLikeCount(id);
    }

    public void decreaseLikeCount(Long id) {
        productCommentMapper.decreaseLikeCount(id);
    }

    public void increaseStampCount(Long id) {
        productCommentMapper.increaseStampCount(id);
    }

    public void decreaseStampCount(Long id) {
        productCommentMapper.decreaseStampCount(id);
    }

    public void increaseFunCount(Long id) {
        productCommentMapper.increaseFunCount(id);
    }

    public void decreaseFunCount(Long id) {
        productCommentMapper.decreaseFunCount(id);
    }
}
