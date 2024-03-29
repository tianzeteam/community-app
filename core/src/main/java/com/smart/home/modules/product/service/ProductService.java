package com.smart.home.modules.product.service;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Splitter;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.FileUtils;
import com.smart.home.enums.EsSaveTypeEnum;
import com.smart.home.es.bean.ProductBean;
import com.smart.home.es.dto.KeyValueDTO;
import com.smart.home.es.service.ProductCommentEsService;
import com.smart.home.es.service.ProductEsService;
import com.smart.home.modules.product.dao.ProductMapper;
import com.smart.home.modules.product.dao.ProductParamSettingMapper;
import com.smart.home.modules.product.dao.ProductParamValueMapper;
import com.smart.home.modules.product.dao.ProductShopMappingMapper;
import com.smart.home.modules.product.entity.*;
import com.smart.home.modules.system.service.SysFileService;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Objects;
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
    @Autowired
    private ProductCommentEsService productCommentEsServiceImpl;
    @Autowired
    private ProductSupportPlatformService productSupportPlatformService;

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
        product.setAverageScore(null);
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
                        // 先检查参数库有没有了
                        List<ProductParamSetting> list = queryProductParamSettingByNameForProduct(productParamValue);
                        if (CollUtil.isNotEmpty(list)) {
                            productParamValue.setParamId(list.get(0).getId());
                        } else {
                            // 添加到参数库
                            productParamValue.setEnableAll(3);
                            ProductParamSetting productParamSetting = new ProductParamSetting();
                            productParamSetting.withParamName(productParamValue.getParamName())
                                    .withRevision(0)
                                    // 说明是自定义的
                                    .withEnableAll(3)
                                    .withCreatedTime(new Date())
                                    .withCreatedBy(product.getCreatedBy());
                            productParamSettingMapper.insertSelective(productParamSetting);
                            productParamValue.setParamId(productParamSetting.getId());
                        }
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
            // 插入支持平台的子表
            createProductSupportPlatformRecord(product.getSupportPlatform(), productId);
            // 同步到es
            ProductBean productBean = new ProductBean();
            BeanUtils.copyProperties(product, productBean);
            productBean.setKeyValueDTOList(paramList);
            productBean.setId(Long.valueOf(productId));
            productBean.setSaveType(EsSaveTypeEnum.PRODUCT.getType());
            productEsServiceImpl.save(productBean);
        }
        syncImages(product);
        return affectRow;
    }

    private List<ProductParamSetting> queryProductParamSettingByNameForProduct(ProductParamValue productParamValue) {
        ProductParamSettingExample productParamSettingExample = new ProductParamSettingExample();
        productParamSettingExample.createCriteria()
                .andParamNameEqualTo(productParamValue.getParamName())
                .andEnableAllEqualTo(3);
        List<ProductParamSetting> list = productParamSettingMapper.selectByExample(productParamSettingExample);
        return list;
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
                        List<ProductParamSetting> list = queryProductParamSettingByNameForProduct(productParamValue);
                        if (CollUtil.isNotEmpty(list)) {
                            productParamValue.setParamId(list.get(0).getId());
                        } else {
                            // 添加到参数库
                            productParamValue.setEnableAll(3);
                            ProductParamSetting productParamSetting = new ProductParamSetting();
                            productParamSetting.withParamName(productParamValue.getParamName())
                                    .withRevision(0)
                                    .withEnableAll(3);
                            productParamSettingMapper.insertSelective(productParamSetting);
                            productParamValue.setParamId(productParamSetting.getId());
                        }
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
                productMapper.saveParams(productId, "[]");
            }
            productShopMappingMapper.deleteByProductId(product.getId());
            if (product.getProductShopMappingList() != null) {
                for (ProductShopMapping productShopMapping : product.getProductShopMappingList()) {
                    productShopMapping.setProductId(productId);
                    productShopMappingMapper.insertSelective(productShopMapping);
                }
                productMapper.saveShops(productId, JSON.toJSONString(product.getProductShopMappingList()));
            } else {
                productMapper.saveShops(productId, "[]");
            }
            // 插入支持平台的子表, 插入前先删除
            productSupportPlatformService.deleteByProductId(productId);
            createProductSupportPlatformRecord(product.getSupportPlatform(), productId);
            // 同步到es
            ProductBean productBean = new ProductBean();
            BeanUtils.copyProperties(findById(productId), productBean);
            productBean.setKeyValueDTOList(paramList);
            productBean.setId(Long.valueOf(productId));
            productBean.setSaveType(EsSaveTypeEnum.PRODUCT.getType());
            productEsServiceImpl.update(productBean);
        }
        syncImages(product);
        return affectRow;
    }

    private void createProductSupportPlatformRecord(String supportPlatform, Integer productId) {
        if (StringUtils.isNotBlank(supportPlatform)) {
            List<String> supportPlatformList = Splitter.on(",").splitToList(supportPlatform);
            for (String platform : supportPlatformList) {
                productSupportPlatformService.create(productId, platform);
            }
        }
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
            // 删除产品对应的参数
            productParamValueMapper.deleteByProductId(id.intValue());
            CompletableFuture.runAsync(()->{
                // 删除es数据
                productEsServiceImpl.deleteById(id);
                // 删除es中的评论
                productCommentEsServiceImpl.deleteByProductId(id.intValue());
            });
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
        if (StringUtils.isBlank(product.getSupportPlatform())) {
            return productMapper.queryByCategory(product);
        } else {
            return productMapper.queryByCategoryAndSupportPlatform(product);
        }
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
