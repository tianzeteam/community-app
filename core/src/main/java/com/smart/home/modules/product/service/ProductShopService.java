package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.FileUtils;
import com.smart.home.modules.product.dao.ProductMapper;
import com.smart.home.modules.product.dao.ProductShopMapper;
import com.smart.home.modules.product.entity.ProductShop;
import com.smart.home.modules.product.entity.ProductShopExample;
import com.smart.home.modules.system.service.SysFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ProductShopService {

    @Resource
    ProductShopMapper productShopMapper;
    @Resource
    ProductMapper productMapper;
    @Resource
    private SysFileService sysFileService;

    public int create(ProductShop productShop) throws ServiceException {
        // 检查唯一性
        ProductShopExample example = new ProductShopExample();
        example.createCriteria().andShopNameEqualTo(productShop.getShopName());
        if (productShopMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该商城已经存在");
        }
        productShop.setCreatedTime(new Date());
        productShop.setRevision(0);
        int affectRow = productShopMapper.insertSelective(productShop);
        String coverImage = productShop.getCoverImage();
        if (StringUtils.isNotBlank(coverImage)) {
            String fileName = FileUtils.getFileNameFromUrl(coverImage);
            sysFileService.sync(fileName);
        }
        return affectRow;
    }

    public int update(ProductShop productShop) throws ServiceException {
        // 检查唯一性
        ProductShopExample example = new ProductShopExample();
        example.createCriteria().andShopNameEqualTo(productShop.getShopName()).andIdNotEqualTo(productShop.getId());
        if (productShopMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该商城已经存在");
        }
        productShop.setUpdatedTime(new Date());
        int affectRow = productShopMapper.updateByPrimaryKeySelective(productShop);
        String coverImage = productShop.getCoverImage();
        if (StringUtils.isNotBlank(coverImage)) {
            String fileName = FileUtils.getFileNameFromUrl(coverImage);
            sysFileService.sync(fileName);
        }
        return affectRow;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) throws ServiceException {
        for (Long id : idList) {
            // 检查有没有挂钩产品了
            if (id == 1 || id == 2) {
                throw new ServiceException("系统内置记录，不能进行删除");
            }
            if (productMapper.countByShopId(id.intValue()) > 0) {
                throw new ServiceException("已经有关联的产品，不能进行删除");
            }
            productShopMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<ProductShop> selectByPage(ProductShop productShop, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductShopExample example = new ProductShopExample();
        ProductShopExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(productShop.getShopName())) {
            criteria.andShopNameLike(productShop.getShopName());
        }
        return productShopMapper.selectByExample(example);
    }

    public ProductShop findById(Long id) {
        ProductShop productShop = productShopMapper.selectByPrimaryKey(id.intValue());
        return productShop;
    }

    public List<ProductShop> queryAllValid() {
        ProductShopExample example = new ProductShopExample();
        return productShopMapper.selectByExample(example);
    }
}
