package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.article.entity.ArticleCommentReply;
import com.smart.home.modules.article.service.ArticleCommentReplyService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "article文章评论的回复接口")
@RestController
@RequestMapping("/api/pc/articleCommentReply")
public class ArticleCommentReplyController {

    @Autowired
    private ArticleCommentReplyService articleCommentReplyService;

    @ApiOperation("创建文章评论的回复")
    @PostMapping("/create")
    public APIResponse create(ArticleCommentReply articleCommentReply) {
        return APIResponse.OK(articleCommentReplyService.create(articleCommentReply));
    }

    @ApiOperation("更新文章评论的回复")
    @PostMapping("/update")
    public APIResponse update(ArticleCommentReply articleCommentReply) {
        return APIResponse.OK(articleCommentReplyService.update(articleCommentReply));
    }

    @ApiOperation("删除文章评论的回复")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        articleCommentReplyService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询文章评论的回复")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ArticleCommentReply>> selectByPage(ArticleCommentReply articleCommentReply, int pageNum, int pageSize) {
        List<ArticleCommentReply> list = articleCommentReplyService.selectByPage(articleCommentReply, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询文章评论的回复")
    @GetMapping("/selectById")
    public APIResponse<ArticleCommentReply> selectById(Long id) {
        return APIResponse.OK(articleCommentReplyService.findById(id));
    }

}