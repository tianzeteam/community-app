package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.article.entity.ArticleProductMapping;
import com.smart.home.modules.article.service.ArticleProductMappingService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "article文章涉及的产品接口")
@RestController
@RequestMapping("/api/pc/articleProductMapping")
public class ArticleProductMappingController {

    @Autowired
    private ArticleProductMappingService articleProductMappingService;

    @ApiOperation("创建文章涉及的产品")
    @PostMapping("/create")
    public APIResponse create(ArticleProductMapping articleProductMapping) {
        return APIResponse.OK(articleProductMappingService.create(articleProductMapping));
    }

    @ApiOperation("更新文章涉及的产品")
    @PostMapping("/update")
    public APIResponse update(ArticleProductMapping articleProductMapping) {
        return APIResponse.OK(articleProductMappingService.update(articleProductMapping));
    }

    @ApiOperation("删除文章涉及的产品")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        articleProductMappingService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询文章涉及的产品")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ArticleProductMapping>> selectByPage(ArticleProductMapping articleProductMapping, int pageNum, int pageSize) {
        List<ArticleProductMapping> list = articleProductMappingService.selectByPage(articleProductMapping, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询文章涉及的产品")
    @GetMapping("/selectById")
    public APIResponse<ArticleProductMapping> selectById(Long id) {
        return APIResponse.OK(articleProductMappingService.findById(id));
    }

}