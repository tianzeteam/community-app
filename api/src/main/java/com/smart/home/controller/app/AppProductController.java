package com.smart.home.controller.app;

import com.smart.home.common.contants.RoleConsts;
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
import com.smart.home.service.CollectService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @ApiOperation("产品列表-分页查询")
    @AnonAccess
    @PostMapping("/queryProductByPage")
    public APIResponse<ResponsePageBean<ProductSearchResultVO>> queryProductByPage(@RequestBody ProductSearchDTO productSearchDTO) {
        ProductSearchResultVO vo = new ProductSearchResultVO();
        // 如果是登陆用户，附加是否收藏过该产品的标记
        Long userId = UserUtils.getLoginUserId();
        if (userId > 0) {
            boolean hasCollect = collectService.hasCollect(CollectTypeEnum.PRODUCT, userId, Long.valueOf(vo.getId()));
            vo.setCollectFlag(hasCollect ? 1 : 0);
        }
        // TODO
        return APIResponse.OK();
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
