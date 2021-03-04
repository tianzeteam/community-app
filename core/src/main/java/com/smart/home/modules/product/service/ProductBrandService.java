package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.FileUtils;
import com.smart.home.modules.product.dao.ProductBrandMapper;
import com.smart.home.modules.product.dao.ProductMapper;
import com.smart.home.modules.product.entity.ProductBrand;
import com.smart.home.modules.product.entity.ProductBrandExample;
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
public class ProductBrandService {

    @Resource
    ProductBrandMapper productBrandMapper;
    @Resource
    ProductMapper productMapper;
    @Resource
    private SysFileService sysFileService;

    public int create(ProductBrand productBrand) {
        // 检查是否存在
        ProductBrandExample example = new ProductBrandExample();
        example.createCriteria().andBrandNameEqualTo(productBrand.getBrandName());
        if (productBrandMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该品牌已经存在");
        }
        productBrand.setCreatedTime(new Date());
        productBrand.setState(RecordStatusEnum.NORMAL.getStatus());
        productBrand.setRevision(0);
        int affectRow = productBrandMapper.insertSelective(productBrand);
        String logo = productBrand.getLogo();
        if (StringUtils.isNotBlank(logo)) {
            String fileName = FileUtils.getFileNameFromUrl(logo);
            sysFileService.sync(fileName);
        }
        return affectRow;
    }

    public int update(ProductBrand productBrand) {
        ProductBrandExample example = new ProductBrandExample();
        example.createCriteria().andBrandNameEqualTo(productBrand.getBrandName()).andIdNotEqualTo(productBrand.getId());
        if (productBrandMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该品牌已经存在");
        }
        productBrand.setUpdatedTime(new Date());
        int affectRow = productBrandMapper.updateByPrimaryKeySelective(productBrand);
        String logo = productBrand.getLogo();
        if (StringUtils.isNotBlank(logo)) {
            String fileName = FileUtils.getFileNameFromUrl(logo);
            sysFileService.sync(fileName);
        }
        return affectRow;
    }

    public int deleteById(Long id) {
        return productBrandMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            // 检查是否关联产品了
            if (productMapper.countByBrandId(id.intValue()) > 0) {
                throw new ServiceException("该品牌已经关联产品，不能进行删除");
            }
            productBrandMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<ProductBrand> selectByPage(ProductBrand productBrand, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductBrandExample example = new ProductBrandExample();
        ProductBrandExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(productBrand.getBrandName())) {
            criteria.andBrandNameLike(productBrand.getBrandName());
        }
        example.setOrderByClause("sort desc");
        return productBrandMapper.selectByExample(example);
    }

    public ProductBrand findById(Long id) {
        ProductBrand productBrand = productBrandMapper.selectByPrimaryKey(id.intValue());
        return productBrand;
    }

    public List<ProductBrand> queryAllValid() {
        ProductBrandExample example = new ProductBrandExample();
        ProductBrandExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(RecordStatusEnum.NORMAL.getStatus());
        example.setOrderByClause("sort desc");
        return productBrandMapper.selectByExample(example);
    }
}
