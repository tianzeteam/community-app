package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "article文章接口")
@RestController
@RequestMapping("/api/pc/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("创建文章")
    @PostMapping("/create")
    public APIResponse create(Article article) {
        article.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(articleService.create(article));
    }

    @ApiOperation("更新文章")
    @PostMapping("/update")
    public APIResponse update(Article article) {
        article.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(articleService.update(article));
    }

    @ApiOperation("删除文章")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        articleService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询文章")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<Article>> selectByPage(Article article, int pageNum, int pageSize) {
        List<Article> list = articleService.selectByPage(article, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询文章")
    @GetMapping("/selectById")
    public APIResponse<Article> selectById(Long id) {
        return APIResponse.OK(articleService.findById(id));
    }

}