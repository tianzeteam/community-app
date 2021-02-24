package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductCommentLikeHistory;
import com.smart.home.modules.product.service.ProductCommentLikeHistoryService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "product产品评价有趣历史接口")
@RestController
@RequestMapping("/api/pc/productCommentLikeHistory")
public class ProductCommentLikeHistoryController {

    @Autowired
    private ProductCommentLikeHistoryService productCommentLikeHistoryService;

    @ApiOperation("分页查询产品评价有趣历史")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductCommentLikeHistory>> selectByPage(ProductCommentLikeHistory productCommentLikeHistory, int pageNum, int pageSize) {
        List<ProductCommentLikeHistory> list = productCommentLikeHistoryService.selectByPage(productCommentLikeHistory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品评价有趣历史")
    @GetMapping("/selectById")
    public APIResponse<ProductCommentLikeHistory> selectById(Long id) {
        return APIResponse.OK(productCommentLikeHistoryService.findById(id));
    }

}