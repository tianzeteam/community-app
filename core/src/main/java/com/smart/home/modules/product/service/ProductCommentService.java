package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.product.dao.ProductCommentMapper;
import com.smart.home.modules.product.entity.Product;
import com.smart.home.modules.product.entity.ProductComment;
import com.smart.home.modules.product.entity.ProductCommentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author jason
 **/
@Service
public class ProductCommentService {

    @Resource
    ProductCommentMapper productCommentMapper;
    @Autowired
    private ProductService productService;

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

    public List<ProductComment> queryCommentByPage(Long loginUserId, int productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return productCommentMapper.queryCommentByPage(loginUserId, productId);
    }

    public ProductComment queryCommentDetailHead(Long userId, Long productCommentId) {
        return productCommentMapper.queryCommentDetailHead(userId, productCommentId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void create(Long loginUserId, BigDecimal startCount, String details, Integer productId) {
        ProductComment productComment = new ProductComment();
        productComment.withCreatedTime(new Date())
                .withDetails(details)
                .withFunCount(0)
                .withLikeCount(0)
                .withProductId(productId)
                .withReplyCount(0)
                .withStampCount(0)
                .withStarCount(startCount)
                .withUserId(loginUserId);
        productCommentMapper.insertSelective(productComment);
        // 超出产品，计算产品的平均分
        Product product = productService.findById(productId);
        BigDecimal averageScore = product.getAverageScore();
        if (Objects.isNull(averageScore)) {
            averageScore = new BigDecimal(10);
        }
        averageScore = startCount.multiply(new BigDecimal(2)).add(averageScore).divide(new BigDecimal(2), 1, RoundingMode.HALF_UP);
        if (startCount.compareTo(new BigDecimal(4)) > 0) {
            product.setFiveStarCount(product.getFiveStarCount() + 1);
        } else if (startCount.compareTo(new BigDecimal(3)) > 0) {
            product.setFourStarCount(product.getFourStarCount() + 1);
        } else if (startCount.compareTo(new BigDecimal(2)) > 0) {
            product.setThreeStarCount(product.getThreeStarCount() + 1);
        } else if (startCount.compareTo(new BigDecimal(1)) > 0) {
            product.setTwoStarCount(product.getTwoStarCount() + 1);
        } else {
            product.setOneStarCount(product.getOneStarCount() + 1);
        }
        productService.update(product);
    }

}
