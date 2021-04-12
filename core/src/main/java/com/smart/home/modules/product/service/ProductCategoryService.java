package com.smart.home.modules.product.service;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.FileUtils;
import com.smart.home.modules.product.dao.ProductCategoryMapper;
import com.smart.home.modules.product.dao.ProductMapper;
import com.smart.home.modules.product.dao.ProductParamSettingMapper;
import com.smart.home.modules.product.entity.ProductCategory;
import com.smart.home.modules.product.entity.ProductCategoryExample;
import com.smart.home.modules.system.service.SysFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    @Autowired
    private ProductCategoryParamService productCategoryParamService;
    @Autowired
    private SysFileService sysFileService;

    @Transactional(rollbackFor = RuntimeException.class)
    public int create(ProductCategory productCategory, List<Integer> paramIdList) throws ServiceException {
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
            Integer level = productCategoryMapper.findLevelById(productCategory.getPid());
            if (Objects.isNull(level)) {
                throw new ServiceException("父级类目不存在");
            }
            productCategory.setLevel(level+1);
        }
        int affectRow = productCategoryMapper.insertSelective(productCategory);
        int productCategoryId = productCategory.getId();
        if (!CollectionUtils.isEmpty(paramIdList)) {
            int sort = 0;
            for (Integer productParamId : paramIdList) {
                productCategoryParamService.create(productCategoryId, productParamId, sort);
                sort ++;
            }
        }
        String icon = productCategory.getIcon();
        if (StringUtils.isNotBlank(icon)) {
            String fileName = FileUtils.getFileNameFromUrl(icon);
            sysFileService.sync(fileName);
        }
        return affectRow;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public int update(ProductCategory productCategory, List<Integer> paramIdList) throws ServiceException {
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
            Integer level = productCategoryMapper.findLevelById(productCategory.getPid());
            if (Objects.isNull(level)) {
                throw new ServiceException("父级类目不存在");
            }
            productCategory.setLevel(level+1);
        }
        productCategoryParamService.deleteByProductCategoryId(productCategory.getId());
        if (CollUtil.isNotEmpty(paramIdList)) {
            int sort = 0;
            for (Integer productParamId : paramIdList) {
                productCategoryParamService.create(productCategory.getId(), productParamId, sort);
                sort ++;
            }
        }
        int affectRow = productCategoryMapper.updateByPrimaryKeySelective(productCategory);
        String icon = productCategory.getIcon();
        if (StringUtils.isNotBlank(icon)) {
            String fileName = FileUtils.getFileNameFromUrl(icon);
            sysFileService.sync(fileName);
        }
        return affectRow;
    }

    public int deleteById(Long id) {
        return productCategoryMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) throws ServiceException {
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

    public ProductCategory findById(Integer id) {
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(id);
        // 查询挂钩的产品参数
        List<Integer> paramIdList = productCategoryParamService.findParamIdListByCategoryId(id);
        productCategory.setParamIdList(paramIdList);
        return productCategory;
    }

    public List<ProductCategory> queryAllValidByPid(int pid) {
        ProductCategoryExample example = new ProductCategoryExample();
        example.createCriteria().andPidEqualTo(pid).andStateEqualTo(RecordStatusEnum.NORMAL.getStatus());
        example.setOrderByClause("sort desc");
        List<ProductCategory> list = productCategoryMapper.selectByExample(example);
        for (ProductCategory productCategory : list) {
            productCategory.setParamIdList(productCategoryParamService.findParamIdListByCategoryId(productCategory.getId()));
        }
        return list;
    }
    //====================导入用到的相关方法====================
    /**
     * 根据分类名称查找分类对象
     * @param categoryName
     * @param level
     * @return
     */
    public ProductCategory findByName(String categoryName, Integer level, Integer pid) {
        ProductCategoryExample example = new ProductCategoryExample();
        example.createCriteria().andTitleEqualTo(categoryName).andLevelEqualTo(level).andPidEqualTo(pid);
        List<ProductCategory> list = productCategoryMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public ProductCategory createNewCategory(String categoryName, int level, Integer pid) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.withTitle(categoryName)
                .withSort(0)
                .withState(RecordStatusEnum.NORMAL.getStatus())
                .withCreatedTime(new Date())
                .withCreatedBy(0L)
                .withPid(pid)
                .withRevision(0);
        productCategory.setLevel(level);
        if (level == 1) {
            productCategory.setPid(0);
        }
        productCategoryMapper.insertSelective(productCategory);
        return productCategory;
    }
}
