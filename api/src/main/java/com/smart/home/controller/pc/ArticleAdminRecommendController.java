package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.SubjectCardCreateDTO;
import com.smart.home.controller.pc.response.article.ArticleAdminRcommendSearchDTO;
import com.smart.home.controller.pc.response.article.ArticleAdminRecommendPageVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.ArticleStateEnum;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.other.entity.SubjectCard;
import com.smart.home.modules.other.service.SubjectCardService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Api(tags = "投稿管理-推荐列表")
@RestController
@RequestMapping("/api/pc/articleAdminRecommend")
public class ArticleAdminRecommendController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private SubjectCardService subjectCardService;

    @ApiOperation("查询出所有的置顶文章")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/selectAllTopRecommend")
    public APIResponse<ResponsePageBean<ArticleAdminRecommendPageVO>> selectAllTopRecommend() {
        List<Article> list = articleService.selectAllTopRecommend();
        List<ArticleAdminRecommendPageVO> resultList = BeanCopyUtils.convertListTo(list, ArticleAdminRecommendPageVO::new, (s, t) -> {
            t.setAuthorId(s.getUserId());
            t.setArticleId(s.getId());
        });
        return APIResponse.OK(ResponsePageBean.restPage(resultList));
    }

    @ApiOperation("分页查询文章")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ArticleAdminRecommendPageVO>> selectByPage(@RequestBody ArticleAdminRcommendSearchDTO articleAdminRcommendSearchDTO) {
        Article article = new Article();
        int pageNum = articleAdminRcommendSearchDTO.getPageNum();
        int pageSize = articleAdminRcommendSearchDTO.getPageSize();
        String sortType = articleAdminRcommendSearchDTO.getSortType();
        String sortField = articleAdminRcommendSearchDTO.getSortField();
        BeanUtils.copyProperties(articleAdminRcommendSearchDTO, article);
        // 只查发布的
        article.setState(ArticleStateEnum.PUBLISH.getState());
        // 只查过审的和推荐的
        article.setAuditState(AuditStatusEnum.APPROVED.getCode());
        article.setRecommendFlag(YesNoEnum.YES.getCode());
        List<Article> list = articleService.selectByPage(article, pageNum, pageSize, sortType, sortField);
        List<ArticleAdminRecommendPageVO> resultList = BeanCopyUtils.convertListTo(list, ArticleAdminRecommendPageVO::new, (s, t) -> {
            t.setAuthorId(s.getUserId());
            t.setArticleId(s.getId());
        });
        return APIResponse.OK(ResponsePageBean.restPage(resultList));
    }

    @ApiOperation("取消置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/cancelSetTop")
    public APIResponse cancelSetTop(Long articleId) {
        articleService.cancelSetTop(articleId);
        return APIResponse.OK();
    }

    @ApiOperation("置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/setTop")
    public APIResponse setTop(Long articleId) {
        articleService.setTop(articleId);
        return APIResponse.OK();
    }

    @ApiOperation("设为大图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/setBigImage")
    public APIResponse setBigImage(Long articleId) {
        articleService.setBigImage(articleId);
        return APIResponse.OK();
    }

    @ApiOperation("设为文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/setAsArticle")
    public APIResponse setAsArticle(Long articleId) {
        articleService.setAsArticle(articleId);
        return APIResponse.OK();
    }

    @ApiOperation("撤回推荐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/cancelRecommend")
    public APIResponse cancelRecommend(Long articleId) {
        articleService.cancelRecommend(Arrays.asList(articleId), UserUtils.getLoginUserId());
        return APIResponse.OK();
    }

    @ApiOperation("插入专题")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/insertSubject")
    public APIResponse insertSubject(@Valid @RequestBody SubjectCardCreateDTO subjectCardCreateDTO, BindingResult bindingResult) {
        SubjectCard subjectCard = new SubjectCard();
        BeanUtils.copyProperties(subjectCardCreateDTO, subjectCard);
        subjectCard.setCreatedBy(UserUtils.getLoginUserId());
        subjectCardService.create(subjectCard);
        return APIResponse.OK();
    }

}
