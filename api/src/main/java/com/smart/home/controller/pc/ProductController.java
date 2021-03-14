package com.smart.home.controller.pc;

import com.alibaba.fastjson.JSON;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.product.*;
import com.smart.home.controller.pc.response.product.*;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.product.entity.*;
import com.smart.home.modules.product.service.*;
import com.smart.home.modules.system.entity.SysDict;
import com.smart.home.modules.system.service.SysDictService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author jason
 **/
@Api(tags = "产品信息/产品库")
@RestController
@RequestMapping("/api/pc/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductShopService productShopService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductParamSettingService productParamSettingService;
    @Autowired
    private ProductBrandService productBrandService;
    @Autowired
    private SysDictService sysDictService;

    @ApiOperation("查询所有商城")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/queryAllShop")
    public APIResponse<List<ProductShopSelectVO>> queryAllShop() {
        List<ProductShop> list = productShopService.queryAllValid();
        List<ProductShopSelectVO> resultList = BeanCopyUtils.convertListTo(list, ProductShopSelectVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("选择一级类目")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/queryCategoryOne")
    public APIResponse<List<ProductCategorySelectVO>> queryCategoryOne() {
        List<ProductCategory> list = productCategoryService.queryAllValidByPid(0);
        List<ProductCategorySelectVO> resultList = BeanCopyUtils.convertListTo(list, ProductCategorySelectVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("选择二级类目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "上级类目主键id", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/queryCategoryTwo")
    public APIResponse<List<ProductCategorySelectVO>> queryCategoryTwo(Integer pid) {
        List<ProductCategory> list = productCategoryService.queryAllValidByPid(pid);
        List<ProductCategorySelectVO> resultList = BeanCopyUtils.convertListTo(list, ProductCategorySelectVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("选择三级类目")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/queryCategoryThree")
    public APIResponse<List<ProductCategorySelectVO>> queryCategoryThree(Integer pid) {
        List<ProductCategory> list = productCategoryService.queryAllValidByPid(pid);
        List<ProductCategorySelectVO> resultList = BeanCopyUtils.convertListTo(list, ProductCategorySelectVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("查询产品下的设置参数")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/queryProductParamSetting")
    public APIResponse<List<ProductParamSettingSelectVO>> queryProductParamSetting() {
        List<ProductParamSetting> list = productParamSettingService.queryAllValidWithEnableAll();
        List<ProductParamSettingSelectVO> resultList = BeanCopyUtils.convertListTo(list, ProductParamSettingSelectVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("查询类目下的设置参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "类目主键id", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/queryProductCatetoryParamSetting")
    public APIResponse<List<ProductParamSettingSelectVO>> queryProductCatetoryParamSetting(Integer categoryId) {
        List<ProductParamSetting> list = productParamSettingService.queryAllValidExceptEnableAll(categoryId);
        List<ProductParamSettingSelectVO> resultList = BeanCopyUtils.convertListTo(list, ProductParamSettingSelectVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("查询产品品牌")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/queryAllBrand")
    public APIResponse<List<ProductBrandSelectVO>> queryAllBrand() {
        List<ProductBrand> list = productBrandService.queryAllValid();
        List<ProductBrandSelectVO> resutList = BeanCopyUtils.convertListTo(list, ProductBrandSelectVO::new);
        return APIResponse.OK(resutList);
    }

    @ApiOperation("查询所有支持平台")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/queryAllSupportPlatform")
    public APIResponse<String> queryAllSupportPlatform() {
        SysDict sysDict = sysDictService.queryByDictCode("product.support.platform");
        if (Objects.isNull(sysDict)) {
            return APIResponse.ERROR("product.support.platform的数据字典还未设置");
        }
        return APIResponse.OK(sysDict.getDictValue());
    }

    @ApiOperation("创建产品")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/create")
    public APIResponse create(@Valid @RequestBody ProductCreateDTO productCreateDTO, BindingResult bindingResult) {
        Product product = new Product();
        BeanUtils.copyProperties(productCreateDTO, product);
        if (!CollectionUtils.isEmpty(productCreateDTO.getImageList())) {
            product.setBannerImages(JSON.toJSONString(productCreateDTO.getImageList()));
        }
        if (!CollectionUtils.isEmpty(productCreateDTO.getTagList())) {
            product.setTag(JSON.toJSONString(productCreateDTO.getTagList()));
        }
        if (!CollectionUtils.isEmpty(productCreateDTO.getBuyLinkDTOList())) {
            List<ProductShopMapping> productShopMappingList = new ArrayList<>();
            for (ProductShopBuyLinkDTO productShopBuyLinkDTO : productCreateDTO.getBuyLinkDTOList()) {
                ProductShopMapping productShopMapping = new ProductShopMapping();
                productShopMapping.withShopIcon(productShopBuyLinkDTO.getCoverImage())
                        .withShopId(productShopBuyLinkDTO.getId())
                        .withShopName(productShopBuyLinkDTO.getShopName())
                        .withUrl(productShopBuyLinkDTO.getUrl());
                productShopMappingList.add(productShopMapping);
            }
            product.setProductShopMappingList(productShopMappingList);
        }
        if (!CollectionUtils.isEmpty(productCreateDTO.getParamValueDTOList())) {
            List<ProductParamValue> productParamValueList = new ArrayList<>();
            for (ProductParamValueDTO productParamValueDTO : productCreateDTO.getParamValueDTOList()) {
                ProductParamValue productParamValue = new ProductParamValue();
                productParamValue.withParamId(productParamValueDTO.getId())
                        .withParamValue(productParamValueDTO.getParamValue())
                        .withSort(productParamValueDTO.getSort());
                productParamValueList.add(productParamValue);
            }
        }
        if (productCreateDTO.getCategoryOneDTO() != null) {
            product.setCategoryOneId(productCreateDTO.getCategoryOneDTO().getId());
            product.setCategoryOneName(productCreateDTO.getCategoryOneDTO().getTitle());
        }
        if (productCreateDTO.getCategoryTwoDTO() != null) {
            product.setCategoryTwoId(productCreateDTO.getCategoryTwoDTO().getId());
            product.setCategoryTwoName(productCreateDTO.getCategoryTwoDTO().getTitle());
        }
        if (productCreateDTO.getCategoryThreeDTO() != null) {
            product.setCategoryThreeId(productCreateDTO.getCategoryThreeDTO().getId());
            product.setCategoryThreeName(productCreateDTO.getCategoryThreeDTO().getTitle());
        }
        if (productCreateDTO.getProductBrandDTO() != null) {
            product.setBrandId(productCreateDTO.getProductBrandDTO().getId());
            product.setBrandName(productCreateDTO.getProductBrandDTO().getBrandName());
        }
        product.setCreatedBy(UserUtils.getLoginUserId());
        try {
            return APIResponse.OK(productService.create(product));
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
    }

    @ApiOperation("更新产品")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/update")
    public APIResponse update(@Valid @RequestBody ProductUpdateDTO productUpdateDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productUpdateDTO, product);
        if (!CollectionUtils.isEmpty(productUpdateDTO.getImageList())) {
            product.setBannerImages(JSON.toJSONString(productUpdateDTO.getImageList()));
        }
        if (!CollectionUtils.isEmpty(productUpdateDTO.getTagList())) {
            product.setTag(JSON.toJSONString(productUpdateDTO.getTagList()));
        }
        if (!CollectionUtils.isEmpty(productUpdateDTO.getBuyLinkDTOList())) {
            List<ProductShopMapping> productShopMappingList = new ArrayList<>();
            for (ProductShopBuyLinkDTO productShopBuyLinkDTO : productUpdateDTO.getBuyLinkDTOList()) {
                ProductShopMapping productShopMapping = new ProductShopMapping();
                productShopMapping.withShopIcon(productShopBuyLinkDTO.getCoverImage())
                        .withShopId(productShopBuyLinkDTO.getId())
                        .withShopName(productShopBuyLinkDTO.getShopName())
                        .withProductId(productUpdateDTO.getId())
                        .withUrl(productShopBuyLinkDTO.getUrl());
                productShopMappingList.add(productShopMapping);
            }
            product.setProductShopMappingList(productShopMappingList);
        }
        if (!CollectionUtils.isEmpty(productUpdateDTO.getParamValueDTOList())) {
            List<ProductParamValue> productParamValueList = new ArrayList<>();
            for (ProductParamValueDTO productParamValueDTO : productUpdateDTO.getParamValueDTOList()) {
                ProductParamValue productParamValue = new ProductParamValue();
                productParamValue.withParamId(productParamValueDTO.getId())
                        .withParamValue(productParamValueDTO.getParamValue())
                        .withProductId(productUpdateDTO.getId())
                        .withSort(productParamValueDTO.getSort());
                productParamValueList.add(productParamValue);
            }
        }
        if (productUpdateDTO.getCategoryOneDTO() != null) {
            product.setCategoryOneId(productUpdateDTO.getCategoryOneDTO().getId());
            product.setCategoryOneName(productUpdateDTO.getCategoryOneDTO().getTitle());
        }
        if (productUpdateDTO.getCategoryTwoDTO() != null) {
            product.setCategoryTwoId(productUpdateDTO.getCategoryTwoDTO().getId());
            product.setCategoryTwoName(productUpdateDTO.getCategoryTwoDTO().getTitle());
        }
        if (productUpdateDTO.getCategoryThreeDTO() != null) {
            product.setCategoryThreeId(productUpdateDTO.getCategoryThreeDTO().getId());
            product.setCategoryThreeName(productUpdateDTO.getCategoryThreeDTO().getTitle());
        }
        if (productUpdateDTO.getProductBrandDTO() != null) {
            product.setBrandId(productUpdateDTO.getProductBrandDTO().getId());
            product.setBrandName(productUpdateDTO.getProductBrandDTO().getBrandName());
        }
        product.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productService.update(product));
    }

    @ApiOperation("按主键ID查询产品出来更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品主键id", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/selectToUpdate")
    public APIResponse<ProductUpdateDTO> selectById(Integer id) {
        Product product = productService.findById(id);
        ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
        BeanUtils.copyProperties(product, productUpdateDTO);
        if (StringUtils.isNotBlank(product.getBannerImages())) {
            productUpdateDTO.setImageList(JSON.parseArray(product.getBannerImages(), String.class));
        }
        if (StringUtils.isNotBlank(product.getTag())) {
            productUpdateDTO.setTagList(JSON.parseArray(product.getTag(), String.class));
        }
        productUpdateDTO.setCategoryOneDTO(new ProductCategoryDTO(product.getCategoryOneId(), product.getCategoryOneName()));
        productUpdateDTO.setCategoryTwoDTO(new ProductCategoryDTO(product.getCategoryTwoId(), product.getCategoryTwoName()));
        productUpdateDTO.setCategoryThreeDTO(new ProductCategoryDTO(product.getCategoryThreeId(), product.getCategoryThreeName()));
        productUpdateDTO.setProductBrandDTO(new ProductBrandDTO(product.getBrandId(), product.getBrandName()));
        if (StringUtils.isNotBlank(product.getParams())) {
            productUpdateDTO.setParamValueDTOList(JSON.parseArray(product.getParams(), ProductParamValueDTO.class));
        }
        if (StringUtils.isNotBlank(product.getShops())) {
            productUpdateDTO.setBuyLinkDTOList(JSON.parseArray(product.getShops(), ProductShopBuyLinkDTO.class));
        }
        return APIResponse.OK(productUpdateDTO);
    }

    @ApiOperation("删除产品")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("隐藏产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品主键id", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/hide")
    public APIResponse hide(Integer productId) {
        productService.updateOnlineStatus(productId, YesNoEnum.NO.getCode());
        return APIResponse.OK();
    }
    @ApiOperation("恢复正常产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品主键id", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/restore")
    public APIResponse restore(Integer productId) {
        productService.updateOnlineStatus(productId, YesNoEnum.YES.getCode());
        return APIResponse.OK();
    }

    @ApiOperation("管理后台-产品库-分页查询")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductPageVO>> selectByPage(@RequestBody ProductPageSearchDTO productPageSearchDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productPageSearchDTO, product);
        product.setTagList(productPageSearchDTO.getTagList());
        List<Product> list = productService.selectByPage(product, productPageSearchDTO.getPageNum(), productPageSearchDTO.getPageSize());
        List<ProductPageVO> resultList = BeanCopyUtils.convertListTo(list, ProductPageVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }

}