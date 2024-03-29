package com.smart.home.controller.app;

import com.alibaba.fastjson.JSON;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.request.ProductSearchDTO;
import com.smart.home.controller.app.response.product.*;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.CollectTypeEnum;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.product.entity.Product;
import com.smart.home.modules.product.entity.ProductComment;
import com.smart.home.modules.product.entity.ProductParamValue;
import com.smart.home.modules.product.entity.ProductShopMapping;
import com.smart.home.modules.product.service.ProductCommentService;
import com.smart.home.modules.product.service.ProductService;
import com.smart.home.modules.system.service.SysDictService;
import com.smart.home.service.CollectService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/25
 **/
@Api(tags = "产品库-产品列表/产品页")
@RestController
@RequestMapping("/api/app/product")
public class AppProductController {

    @Autowired
    private CollectService collectService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCommentService productCommentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SysDictService sysDictService;


    @ApiOperation("产品列表-分页查询")
    @AnonAccess
    @PostMapping("/queryProductByPage")
    public APIResponse<ResponsePageBean<ProductSearchResultVO>> queryProductByPage(@RequestBody ProductSearchDTO productSearchDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productSearchDTO, product);
        List<Product> list = productService.queryByCategory(product, productSearchDTO.getPageNum(), productSearchDTO.getPageSize());
        List<ProductSearchResultVO> resultList = BeanCopyUtils.convertListTo(list, ProductSearchResultVO::new, (s, t)->{
            if (StringUtils.isNotBlank(s.getTag())) {
                t.setTagList(JSON.parseArray(s.getTag(), String.class));
            }
        });
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }

    @ApiOperation("产品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/queryProductDetail")
    public APIResponse<ProductDetailVO> queryProductDetail(@RequestParam(required = true) Integer productId) {
        Product product = productService.queryDetailById(productId, UserUtils.getLoginUserId());
        ProductDetailVO productDetailVO = BeanCopyUtils.convertTo(product, ProductDetailVO::new, (s, t)->{
           if (StringUtils.isNotBlank(s.getBannerImages())) {
               t.setBannerImageList(JSON.parseArray(s.getBannerImages(), String.class));
           } else {
               t.setBannerImageList(Collections.EMPTY_LIST);
           }
           if (StringUtils.isNotBlank(s.getTag())) {
               t.setTagList(JSON.parseArray(s.getTag(), String.class));
           }
           if (StringUtils.isNotBlank(s.getParams()) && !"[]".equals(s.getParams())) {
               t.setProductParamValueList(JSON.parseArray(s.getParams(), ProductParamValue.class));
           } else {
               t.setProductShopMappingList(Collections.EMPTY_LIST);
           }
           if (StringUtils.isNotBlank(s.getShops()) && !"[]".equals(s.getShops())) {
              t.setProductShopMappingList(JSON.parseArray(s.getShops(), ProductShopMapping.class));
           } else {
               t.setProductShopMappingList(Collections.EMPTY_LIST);
           }
        });
        // 我有没有收藏过
        Long userId = UserUtils.getLoginUserId();
        if (userId > 0) {
            boolean hasCollect = collectService.hasCollect(CollectTypeEnum.PRODUCT, userId, Long.valueOf(productId));
            productDetailVO.setCollectFlag(hasCollect ? YesNoEnum.YES.getCode() : YesNoEnum.NO.getCode());
        } else {
            productDetailVO.setCollectFlag(YesNoEnum.NO.getCode());
        }
        productDetailVO.setMyScore(productCommentService.queryStarCountByUser(UserUtils.getLoginUserId(), productDetailVO.getId()));
        productDetailVO.setHref(sysDictService.queryValueByDictCode("share.link.prefix.product")+"?id="+productDetailVO.getId());
        return APIResponse.OK(productDetailVO);
    }

    @ApiOperation("根据产品主键id查询评价-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品主键id", required = true),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量 ", required = true)
    })
    @AnonAccess
    @PostMapping("/queryProductCommentByPage")
    public APIResponse<ResponsePageBean<ProductPageCommentVO>> queryProductCommentByPage(Integer productId, int pageNum, int pageSize) {
        Long userId = UserUtils.getLoginUserId();
        List<ProductComment> list = productCommentService.queryViaProductIdByPage(productId,userId, pageNum, pageSize);
        List<ProductPageCommentVO> resultList = BeanCopyUtils.convertListTo(list, ProductPageCommentVO::new, (s,t)->{
            t.setCommentId(s.getId());
            t.setFunFlag(s.getFunId() == null ? 0 : 1);
            t.setLikeFlag(s.getLikeId() == null ? 0 : 1);
            t.setStampFlag(s.getStampId() == null ? 0 : 1);
            if (StringUtils.isNotBlank(s.getImages())) {
                t.setImageList(JSON.parseArray(s.getImages(), String.class));
            }
        });
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }

    @ApiOperation("根据产品主键id查询评测-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品主键id", required = true),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量 ", required = true)
    })
    @AnonAccess
    @PostMapping("/queryProductTestByPage")
    public APIResponse<ResponsePageBean<ProductDetailPageTestVO>> queryProductTestByPage(Integer productId, int pageNum, int pageSize) {
        List<Article> list = articleService.queryViaProductIdByPage(productId, pageNum, pageSize);
        List<ProductDetailPageTestVO> resultList = BeanCopyUtils.convertListTo(list, ProductDetailPageTestVO::new,(s,t)->{
            if (StringUtils.isNotBlank(s.getBannerImages())) {
                t.setImageList(JSON.parseArray(s.getBannerImages(), String.class));
            }
        });
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }

    @ApiOperation("收藏产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/collectProduct")
    public APIResponse collectProduct(Long productId) {
        Long userId = UserUtils.getLoginUserId();
        try {
            collectService.addCollect(CollectTypeEnum.PRODUCT, userId, productId);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("收藏/取消收藏产品-二合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品主键id", required = true),
            @ApiImplicitParam(name = "action", value = "0收藏1取消收藏", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/collectOrCancelCollectProduct")
    public APIResponse collectOrCancelCollectProduct(@RequestParam(required = true) Long productId,@RequestParam(required = true) Integer action) {
        Long userId = UserUtils.getLoginUserId();
        try {
            if (action == 0) {
                collectService.addCollect(CollectTypeEnum.PRODUCT, userId, productId);
            } else {
                collectService.cancelCollect(CollectTypeEnum.PRODUCT, userId, productId);
            }
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

}
