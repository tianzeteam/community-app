package com.smart.home.modules.product.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.FileUtils;
import com.smart.home.es.bean.ProductBean;
import com.smart.home.es.dto.KeyValueDTO;
import com.smart.home.es.service.ProductEsService;
import com.smart.home.modules.product.dao.ProductMapper;
import com.smart.home.modules.product.dao.ProductParamSettingMapper;
import com.smart.home.modules.product.dao.ProductParamValueMapper;
import com.smart.home.modules.product.dao.ProductShopMappingMapper;
import com.smart.home.modules.product.entity.*;
import com.smart.home.modules.system.entity.SysFile;
import com.smart.home.modules.system.service.SysFileService;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * @author jason
 **/
@Service
public class ProductService {

    @Resource
    ProductMapper productMapper;
    @Resource
    ProductParamValueMapper productParamValueMapper;
    @Resource
    ProductShopMappingMapper productShopMappingMapper;
    @Resource
    ProductParamSettingMapper productParamSettingMapper;
    @Autowired
    private SysFileService sysFileService;
    @Autowired
    private ProductEsService productEsServiceImpl;

    @Transactional(rollbackFor = RuntimeException.class)
    public int create(Product product) throws ServiceException {
        // 查重
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        if (product.getCategoryOneId() != null) {
            criteria.andCategoryOneIdEqualTo(product.getCategoryOneId());
        }
        if (product.getCategoryTwoId() != null) {
            criteria.andCategoryTwoIdEqualTo(product.getCategoryTwoId());
        }
        if (product.getCategoryThreeId() != null) {
            criteria.andCategoryThreeIdEqualTo(product.getCategoryThreeId());
        }
        if (StringUtils.isNotBlank(product.getProductName())) {
            criteria.andProductNameEqualTo(product.getProductName());
        }
        if (StringUtils.isNotBlank(product.getBrandName())) {
            criteria.andBrandNameEqualTo(product.getBrandName());
        }
        if (StringUtils.isNotBlank(product.getSpecification())) {
            criteria.andSpecificationEqualTo(product.getSpecification());
        }
        criteria.andDeleteFlagEqualTo(YesNoEnum.NO.getCode());
        if (productMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该产品已经存在");
        }
        product.setCreatedTime(new Date());
        product.setRevision(0);
        product.setAverageScore(new BigDecimal(10));
        product.setPraiseRate(100);
        product.setCommentCount(0);
        product.setTestCount(0);
        product.setHotRate(new BigDecimal(0));
        product.setCollectCount(0);
        product.setOneStarCount(0);
        product.setTwoStarCount(0);
        product.setThreeStarCount(0);
        product.setFourStarCount(0);
        product.setFiveStarCount(0);
        product.setDeleteFlag(YesNoEnum.NO.getCode());
        product.setRecommendFlag(YesNoEnum.NO.getCode());
        int affectRow = productMapper.insertSelective(product);
        if (affectRow > 0) {
            List<KeyValueDTO> paramList = new ArrayList<>();
            Integer productId = product.getId();
            if (product.getProductParamValueList() != null) {
                for (ProductParamValue productParamValue : product.getProductParamValueList()) {
                    if (productParamValue.getParamId() == null) {
                        // 添加到参数库
                        ProductParamSetting productParamSetting = new ProductParamSetting();
                        productParamSetting.withParamName(productParamValue.getParamName())
                                .withRevision(0);
                        productParamSettingMapper.insertSelective(productParamSetting);
                        productParamValue.setParamId(productParamSetting.getId());
                    }
                    productParamValue.setProductId(productId);
                    productParamValueMapper.insertSelective(productParamValue);
                    KeyValueDTO keyValueDTO = new KeyValueDTO();
                    keyValueDTO.setKey(productParamValue.getParamName());
                    keyValueDTO.setValue(productParamValue.getParamValue());
                    paramList.add(keyValueDTO);
                }
                productMapper.saveParams(productId, JSON.toJSONString(product.getProductParamValueList()));
            }
            if (product.getProductShopMappingList() != null) {
                for (ProductShopMapping productShopMapping : product.getProductShopMappingList()) {
                    productShopMapping.setProductId(productId);
                    productShopMappingMapper.insertSelective(productShopMapping);
                }
                productMapper.saveShops(productId, JSON.toJSONString(product.getProductShopMappingList()));
            }
            // 同步到es
            ProductBean productBean = new ProductBean();
            BeanUtils.copyProperties(product, productBean);
            productBean.setKeyValueDTOList(paramList);
            productEsServiceImpl.save(productBean);
        }
        syncImages(product);
        return affectRow;
    }

    public int update(Product product) {
        product.setUpdatedTime(new Date());
        int affectRow = productMapper.updateByPrimaryKeySelective(product);
        if (affectRow > 0) {
            Integer productId = product.getId();
            productParamValueMapper.deleteByProductId(product.getId());
            List<KeyValueDTO> paramList = new ArrayList<>();
            if (product.getProductParamValueList() != null) {
                for (ProductParamValue productParamValue : product.getProductParamValueList()) {
                    if (productParamValue.getParamId() == null) {
                        // 添加到参数库
                        ProductParamSetting productParamSetting = new ProductParamSetting();
                        productParamSetting.withParamName(productParamValue.getParamName())
                                .withRevision(0);
                        productParamSettingMapper.insertSelective(productParamSetting);
                        productParamValue.setParamId(productParamSetting.getId());
                    }
                    productParamValue.setProductId(productId);
                    productParamValueMapper.insertSelective(productParamValue);
                    KeyValueDTO keyValueDTO = new KeyValueDTO();
                    keyValueDTO.setKey(productParamValue.getParamName());
                    keyValueDTO.setValue(productParamValue.getParamValue());
                    paramList.add(keyValueDTO);
                }
                productMapper.saveParams(productId, JSON.toJSONString(product.getProductParamValueList()));
            } else {
                productMapper.saveParams(productId, "");
            }
            productShopMappingMapper.deleteByProductId(product.getId());
            if (product.getProductShopMappingList() != null) {
                for (ProductShopMapping productShopMapping : product.getProductShopMappingList()) {
                    productShopMapping.setProductId(productId);
                    productShopMappingMapper.insertSelective(productShopMapping);
                }
                productMapper.saveShops(productId, JSON.toJSONString(product.getProductShopMappingList()));
            } else {
                productMapper.saveShops(productId, "");
            }
            // 同步到es
            ProductBean productBean = new ProductBean();
            BeanUtils.copyProperties(findById(productId), productBean);
            productBean.setKeyValueDTOList(paramList);
            productEsServiceImpl.update(productBean);
        }
        syncImages(product);
        return affectRow;
    }

    private void syncImages(Product product) {
        List<String> needSyncFileNameList = new ArrayList<>();
        if (StringUtils.isNotBlank(product.getCoverImage())) {
            needSyncFileNameList.add(FileUtils.getFileNameFromUrl(product.getCoverImage()));
        }
        if (StringUtils.isNotBlank(product.getBannerImages())) {
            List<String> list = JSON.parseArray(product.getBannerImages(), String.class);
            for (String s : list) {
                needSyncFileNameList.add(FileUtils.getFileNameFromUrl(s));
            }
        }
        sysFileService.syncList(needSyncFileNameList);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productMapper.softDelete(id.intValue());
            // 删除es数据
            productEsServiceImpl.deleteById(id.intValue());
        }
    }

    public List<Product> selectByPage(Product product, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (CollectionUtils.isEmpty(product.getTagList())) {
            product.setTagList(null);
        }
        return productMapper.selectByPage(product);
    }

    public Product findById(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    public void increaseCollectCount(Long id) {
        productMapper.increaseCollectCount(id);
    }

    public void decreaseCollectCount(Long id) {
        productMapper.decreaseCollectCount(id);
    }

    public Product queryProductCommentCountInfo(Integer productId) {
        return productMapper.queryProductCommentCountInfo(productId);
    }

    public void updateOnlineStatus(Integer productId, int status) {
        productMapper.updateOnlineStatus(productId, status);
    }

    public List<Product> queryByCategory(Product product, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return productMapper.queryByCategory(product);
    }

    public List<Product> queryCollectViaUserIdByPage(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return productMapper.queryCollectViaUserIdByPage(userId);
    }

    public Product queryDetailById(Integer productId, Long loginUserId) {
        return productMapper.queryDetailById(productId, loginUserId);
    }

    public void updateCommentScore(Integer productId, BigDecimal averageScore, Integer fiveStarCount, Integer fourStarCount,
                                   Integer threeStarCount, Integer twoStarCount, Integer oneStarCount,
                                   Integer praiseRate) {
        productMapper.updateCommentScore(productId, averageScore, fiveStarCount, fourStarCount, threeStarCount, twoStarCount, oneStarCount, praiseRate);
    }

}
