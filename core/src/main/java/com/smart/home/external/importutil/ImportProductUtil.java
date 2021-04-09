package com.smart.home.external.importutil;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.modules.product.entity.*;
import com.smart.home.modules.product.service.ProductBrandService;
import com.smart.home.modules.product.service.ProductCategoryService;
import com.smart.home.modules.product.service.ProductService;
import com.smart.home.modules.product.service.ProductShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/4/8
 * 产品名称 产品分类 产品参数 京东链接 淘宝链接 品牌 产品简介 封面图地址 内页图地址 支持平台
 **/
@Service
public class ImportProductUtil {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductBrandService productBrandService;
    @Autowired
    private ProductShopService productShopService;
    @Autowired
    private ProductService productService;

    public void beginImport() throws Exception {
        String filePath = "C:\\Users\\test\\Documents\\WeChat Files\\qq2266\\FileStorage\\File\\2021-04\\parse_d5_taobao.txt";
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        while((line = bufferedReader.readLine()) != null) {
            List<String> list = Splitter.on("\t").splitToList(line);
            System.out.println(list);
            String productName = list.get(0);
            String productCategories = list.get(1);
            String[] categoryArray = productCategories.split("-");
            String categoryOneName = categoryArray[0];
            ProductCategory productCategoryOne = productCategoryService.findByName(categoryOneName, 1);
            if (Objects.isNull(productCategoryOne)) {
                // 新建一个分类
                productCategoryOne = productCategoryService.createNewCategory(categoryOneName, 1);
            }
            int categoryOneId = productCategoryOne.getId();

            String categoryTwoName = categoryArray[1];
            ProductCategory productCategoryTwo = productCategoryService.findByName(categoryTwoName, 2);
            if (Objects.isNull(productCategoryTwo)) {
                // 新建一个分类
                productCategoryTwo = productCategoryService.createNewCategory(categoryTwoName, 2);
            }
            int categoryTwoId = productCategoryTwo.getId();

            String categoryThreeName = categoryArray[2];
            ProductCategory productCategoryThree = productCategoryService.findByName(categoryThreeName, 3);
            if (Objects.isNull(productCategoryThree)) {
                // 新建一个分类
                productCategoryThree = productCategoryService.createNewCategory(categoryThreeName, 3);
            }
            int categoryThreeId = productCategoryThree.getId();

            String productParamJson = list.get(2);
            List<ProductParamValue> productParamValueList = new ArrayList<>();
            Map<String, String> paramMap = JSON.parseObject(productParamJson, Map.class);
            paramMap.forEach((key, value)->{
                ProductParamValue productParamValue = new ProductParamValue();
                productParamValue.setEnableAll(3);
                productParamValue.setParamName(key);
                productParamValue.setParamValue(value);
                productParamValue.setSort(0);
                productParamValueList.add(productParamValue);
            });

            String jdShopUrl = list.get(3);
            ProductShop jdShop = productShopService.queryJdShop();
            String taobaoShopUrl = list.get(4);
            ProductShop taobaoShop = productShopService.queryTaobaoShop();
            List<ProductShopMapping> productShopMappingList = new ArrayList<>();
            ProductShopMapping jdShopMapping = new ProductShopMapping();
            jdShopMapping.setTitle(jdShop.getShopName());
            jdShopMapping.setShopIcon(jdShop.getCoverImage());
            jdShopMapping.setShopName(jdShop.getShopName());
            jdShopMapping.setShopId(jdShop.getId());
            jdShopMapping.setUrl(jdShopUrl);
            productShopMappingList.add(jdShopMapping);
            ProductShopMapping taobaoShopMapping = new ProductShopMapping();
            taobaoShopMapping.setTitle(taobaoShop.getShopName());
            taobaoShopMapping.setShopIcon(taobaoShop.getCoverImage());
            taobaoShopMapping.setShopName(taobaoShop.getShopName());
            taobaoShopMapping.setShopId(taobaoShop.getId());
            taobaoShopMapping.setUrl(taobaoShopUrl);
            productShopMappingList.add(taobaoShopMapping);


            String productBrandName = list.get(5);
            ProductBrand productBrand =  productBrandService.findByName(productBrandName);
            if (Objects.isNull(productBrand)) {
                productBrand = productBrandService.createNew(productBrandName);
            }
            String productRemark = list.get(6);

            String productCoverImage = list.get(7);

            String productBannerImagesJson = list.get(8);
            List<String> productBannerImagesJsonList = JSON.parseArray(productBannerImagesJson, String.class);

            String productSupportPlatformJson = list.get(9);
            List<String> productSupportPlatformJsonList = JSON.parseArray(productSupportPlatformJson, String.class);
            String productSupportPlatform = Joiner.on(",").join(productSupportPlatformJsonList);

            Product product = new Product();
            product.setCreatedBy(0L);
            product.setImportFlag(YesNoEnum.YES.getCode());
            product.setRemark(productRemark);
            product.setCoverImage(productCoverImage);
            product.setBannerImages(JSON.toJSONString(productBannerImagesJsonList));
            product.setCategoryOneId(categoryOneId);
            product.setCategoryTwoId(categoryTwoId);
            product.setCategoryThreeId(categoryThreeId);
            product.setCategoryOneName(categoryOneName);
            product.setCategoryTwoName(categoryTwoName);
            product.setCategoryThreeName(categoryThreeName);
            product.setProductName(productName);
            product.setBrandId(productBrand.getId());
            product.setBrandName(productBrandName);
            product.setSupportPlatform(productSupportPlatform);
            product.setProductParamValueList(productParamValueList);
            product.setProductShopMappingList(productShopMappingList);

            try {
                productService.create(product);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        bufferedReader.close();
    }

}
