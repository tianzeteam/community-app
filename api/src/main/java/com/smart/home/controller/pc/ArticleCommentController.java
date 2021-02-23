package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.service.ArticleCommentService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "article文章评论接口")
@RestController
@RequestMapping("/api/pc/articleComment")
public class ArticleCommentController {

    @Autowired
    private ArticleCommentService articleCommentService;

    @ApiOperation("创建文章评论")
    @PostMapping("/create")
    public APIResponse create(ArticleComment articleComment) {
        return APIResponse.OK(articleCommentService.create(articleComment));
    }

    @ApiOperation("更新文章评论")
    @PostMapping("/update")
    public APIResponse update(ArticleComment articleComment) {
        return APIResponse.OK(articleCommentService.update(articleComment));
    }

    @ApiOperation("删除文章评论")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        articleCommentService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询文章评论")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ArticleComment>> selectByPage(ArticleComment articleComment, int pageNum, int pageSize) {
        List<ArticleComment> list = articleCommentService.selectByPage(articleComment, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询文章评论")
    @GetMapping("/selectById")
    public APIResponse<ArticleComment> selectById(Long id) {
        return APIResponse.OK(articleCommentService.findById(id));
    }

}