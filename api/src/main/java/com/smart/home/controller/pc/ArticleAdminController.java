package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.response.article.ArticleAdminPageVO;
import com.smart.home.controller.pc.response.article.ArticleAdminRejectDTO;
import com.smart.home.controller.pc.response.article.ArticleAdminSearchDTO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.ArticleStateEnum;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Api(tags = "投稿-管理")
@RestController
@RequestMapping("/api/pc/articleAdmin")
public class ArticleAdminController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("分页查询文章")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ArticleAdminPageVO>> selectByPage(@RequestBody ArticleAdminSearchDTO articleAdminSearchDTO) {
        Article article = new Article();
        int pageNum = articleAdminSearchDTO.getPageNum();
        int pageSize = articleAdminSearchDTO.getPageSize();
        String sortType = articleAdminSearchDTO.getSortType();
        String sortField = articleAdminSearchDTO.getSortField();
        BeanUtils.copyProperties(articleAdminSearchDTO, article);
        // 只查发布的
        article.setState(ArticleStateEnum.PUBLISH.getState());
        List<Article> list = articleService.selectByPage(article, pageNum, pageSize, sortType, sortField);
        List<ArticleAdminPageVO> resultList = BeanCopyUtils.convertListTo(list, ArticleAdminPageVO::new, (s, t) -> {
            t.setAuthorId(s.getUserId());
            t.setArticleId(s.getId());
        });
        return APIResponse.OK(ResponsePageBean.restPage(resultList));
    }

    @ApiOperation("按文章主键id查详细内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @GetMapping("/selectById")
    public APIResponse<Article> selectById(Long articleId) {
        return APIResponse.OK(articleService.findById(articleId));
    }

    @ApiOperation("操作审核通过-支持单个和批量")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/approve")
    public APIResponse approve(@RequestBody IdListBean idListBean) {
        Long userId = UserUtils.getLoginUserId();
        articleService.approveArticleManually(idListBean.getIdList(), userId);
        return APIResponse.OK();
    }

    @ApiOperation("操作审核拒绝-支持单个和批量")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/reject")
    public APIResponse reject(@RequestBody ArticleAdminRejectDTO articleAdminRejectDTO) {
        Long userId = UserUtils.getLoginUserId();
        articleService.rejectArticleManually(articleAdminRejectDTO.getIdList(), articleAdminRejectDTO.getRejectReason(), userId);
        return APIResponse.OK();
    }

}
