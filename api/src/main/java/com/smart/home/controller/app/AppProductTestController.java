package com.smart.home.controller.app;

import com.alibaba.fastjson.JSON;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.product.ProductPageTestVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.util.ResponsePageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/25
 **/
@Api(tags = "产品库-产品评测")
@RestController
@RequestMapping("/api/app/productTest")
public class AppProductTestController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("评测列表-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId",value = "产品主键ID", required = true),
            @ApiImplicitParam(name = "pageNum",value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页数量", required = true)
    })
    @AnonAccess
    @GetMapping("/queryByPage")
    public APIResponse<ResponsePageBean<ProductPageTestVO>> queryByPage(Integer productId, Integer pageNum, Integer pageSize) {
        List<Article> list = articleService.queryTestForProductByPage(productId, pageNum, pageSize);
        List<ProductPageTestVO> resultList = BeanCopyUtils.convertListTo(list, ProductPageTestVO::new,(s,t)->{
            if (StringUtils.isNotBlank(s.getBannerImages())) {
                t.setImageList(JSON.parseArray(s.getBannerImages(), String.class));
            }
        });
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }

}
