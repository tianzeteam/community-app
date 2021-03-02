package com.smart.home.controller.app;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.request.PrimaryKeyPageDTO;
import com.smart.home.controller.app.request.ProductSearchDTO;
import com.smart.home.controller.app.response.product.ProductPageCommentVO;
import com.smart.home.controller.app.response.product.ProductPageTestVO;
import com.smart.home.controller.app.response.product.ProductSearchResultVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.CollectTypeEnum;
import com.smart.home.modules.product.entity.Product;
import com.smart.home.modules.product.service.ProductService;
import com.smart.home.service.CollectService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @ApiOperation("根据产品主键id查询评价-分页查询")
    @AnonAccess
    @PostMapping("/queryProductCommentByPage")
    public APIResponse<ResponsePageBean<ProductPageCommentVO>> queryProductCommentByPage(@RequestBody PrimaryKeyPageDTO primaryKeyPageDTO) {
        // TODO
        return APIResponse.OK();
    }

    @ApiOperation("根据产品主键id查询评测-分页查询")
    @AnonAccess
    @PostMapping("/queryProductTestByPage")
    public APIResponse<ResponsePageBean<ProductPageTestVO>> queryProductTestByPage(@RequestBody PrimaryKeyPageDTO primaryKeyPageDTO) {
        // TODO
        return APIResponse.OK();
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
