package com.smart.home.controller.app;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.request.PrimaryKeyPageDTO;
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
import com.smart.home.modules.product.service.ProductCommentService;
import com.smart.home.modules.product.service.ProductService;
import com.smart.home.service.CollectService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @ApiOperation("产品列表-分页查询")
    @AnonAccess
    @PostMapping("/queryProductByPage")
    public APIResponse<ResponsePageBean<ProductSearchResultVO>> queryProductByPage(@RequestBody ProductSearchDTO productSearchDTO) {
        ProductSearchResultVO vo = new ProductSearchResultVO();
        Product product = new Product();
        BeanUtils.copyProperties(productSearchDTO, product);
        List<Product> list = productService.queryByCategory(product, productSearchDTO.getPageNum(), productSearchDTO.getPageSize());
        List<ProductSearchResultVO> resultList = BeanCopyUtils.convertListTo(list, ProductSearchResultVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }

    @ApiOperation("产品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/queryProductDetail")
    public APIResponse<ProductDetailVO> queryProductDetail(Integer productId) {
        Product product = productService.queryDetailById(productId, UserUtils.getLoginUserId());
        ProductDetailVO productDetailVO = new ProductDetailVO();
        BeanUtils.copyProperties(product, productDetailVO);
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
        List<ProductComment> list = productCommentService.queryViaProductIdByPage(productId, pageNum, pageSize);
        List<ProductPageCommentVO> resultList = BeanCopyUtils.convertListTo(list, ProductPageCommentVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
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
        List<ProductDetailPageTestVO> resultList = BeanCopyUtils.convertListTo(list, ProductDetailPageTestVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }

    @ApiOperation("收藏产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/collectProduct")
    public APIResponse collectProduct(Long productId) {
        Long userId = UserUtils.getLoginUserId();
        collectService.addCollect(CollectTypeEnum.PRODUCT, userId, productId);
        return APIResponse.OK();
    }

}
