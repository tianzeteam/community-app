package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.modules.product.dao.ProductCategoryMapper;
import com.smart.home.modules.product.dao.ProductMapper;
import com.smart.home.modules.product.entity.ProductCategory;
import com.smart.home.modules.product.entity.ProductCategoryExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;
import java.util.Objects;

/**
 * @author jason
 **/
@Service
public class ProductCategoryService {

    @Resource
    ProductCategoryMapper productCategoryMapper;
    @Resource
    ProductMapper productMapper;

    public int create(ProductCategory productCategory) {
        // 检查同级同名
        ProductCategoryExample example = new ProductCategoryExample();
        example.createCriteria().andPidEqualTo(productCategory.getPid())
                .andTitleEqualTo(productCategory.getTitle());
        if (productCategoryMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该类目已经存在");
        }
        productCategory.setRevision(0);
        productCategory.setState(RecordStatusEnum.NORMAL.getStatus());
        productCategory.setCreatedTime(new Date());
        if (new Integer(0).equals(productCategory.getPid())) {
            // 顶级level设置为1
            productCategory.setLevel(1);
        } else {
            // 找出上级目录的level+1
            Integer level = productCategoryMapper.findParentLevel(productCategory.getPid());
            if (Objects.isNull(level)) {
                throw new ServiceException("父级类目不存在");
            }
            productCategory.setLevel(level+1);
        }
        return productCategoryMapper.insertSelective(productCategory);
    }

    public int update(ProductCategory productCategory) {
        ProductCategoryExample example = new ProductCategoryExample();
        example.createCriteria().andPidEqualTo(productCategory.getPid())
                .andTitleEqualTo(productCategory.getTitle()).andIdNotEqualTo(productCategory.getId());
        if (productCategoryMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该类目已经存在");
        }
        productCategory.setUpdatedTime(new Date());
        if (new Integer(0).equals(productCategory.getPid())) {
            // 顶级level设置为1
            productCategory.setLevel(1);
        } else {
            // 找出上级目录的level+1
            Integer level = productCategoryMapper.findParentLevel(productCategory.getPid());
            if (Objects.isNull(level)) {
                throw new ServiceException("父级类目不存在");
            }
            productCategory.setLevel(level+1);
        }
        return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }

    public int deleteById(Long id) {
        return productCategoryMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            // 检查有没有关联到产品了
            int level = productCategoryMapper.findLevelById(id.intValue());
            if (level == 1) {
                if (productMapper.countByCategoryOne(id.intValue()) > 0) {
                    throw new ServiceException("类目下有关联产品，不能删除");
                }
            } else if (level == 2) {
                if (productMapper.countByCategoryTwo(id.intValue()) > 0) {
                    throw new ServiceException("类目下有关联产品，不能删除");
                }
            } else if (level == 3) {
                if (productMapper.countByCategoryThree(id.intValue()) > 0) {
                    throw new ServiceException("类目下有关联产品，不能删除");
                }
            }
            productCategoryMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<ProductCategory> selectByPage(ProductCategory productCategory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductCategoryExample example = new ProductCategoryExample();
        ProductCategoryExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(productCategory.getTitle())) {
            criteria.andTitleEqualTo(productCategory.getTitle());
        }
        if (productCategory.getPid() != null) {
            criteria.andPidEqualTo(productCategory.getPid());
        }
        if (productCategory.getLevel() != null) {
            criteria.andLevelEqualTo(productCategory.getLevel());
        }
        example.setOrderByClause("sort desc");
        return productCategoryMapper.selectByExample(example);
    }

    public ProductCategory findById(Long id) {
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(id.intValue());
        return productCategory;
    }

    public List<ProductCategory> queryAllValidByPid(int pid) {
        ProductCategoryExample example = new ProductCategoryExample();
        example.createCriteria().andPidEqualTo(pid).andStateEqualTo(RecordStatusEnum.NORMAL.getStatus());
        example.setOrderByClause("sort desc");
        return productCategoryMapper.selectByExample(example);
    }
}
