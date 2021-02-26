package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.controller.pc.request.article.ArticleCreateDTO;
import com.smart.home.controller.pc.request.article.ArticleUpdateDTO;
import com.smart.home.controller.pc.response.article.ArticleEditVO;
import com.smart.home.controller.pc.response.article.ArticleEditVideoVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.AgreementTypeEnum;
import com.smart.home.enums.ArticleCategoryEnum;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.system.service.SysAgreementService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "投稿")
@RestController
@RequestMapping("/api/pc/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private SysAgreementService sysAgreementService;

    @ApiOperation("获取内容创作规范")
    @AnonAccess
    @GetMapping("/selectArticleCreateRule")
    public APIResponse selectArticleCreateRule() {
        return APIResponse.OK(sysAgreementService.selectByType(AgreementTypeEnum.ARTICLE_CREATE_RULE));
    }

    @ApiOperation("获取文章投稿协议")
    @AnonAccess
    @GetMapping("/selectArticleCreateAgreement")
    public APIResponse selectArticleCreateAgreement() {
        return APIResponse.OK(sysAgreementService.selectByType(AgreementTypeEnum.ARTICLE_CREATE_AGREEMENT));
    }

    @ApiOperation("投稿/保存草稿文章")
    @RoleAccess({RoleConsts.CREATOR})
    @PostMapping("/create")
    public APIResponse create(@Valid ArticleCreateDTO articleCreateDTO, BindingResult bindingResult) {
        Article article = new Article();
        BeanUtils.copyProperties(articleCreateDTO, article);
        article.setUserId(UserUtils.getLoginUserId());
        article.setCreatedBy(UserUtils.getLoginUserId());
        article.setCategory(ArticleCategoryEnum.CONTENT.getCode());
        return APIResponse.OK(articleService.create(article));
    }

    @ApiOperation("投稿/保存草稿视频")
    @RoleAccess({RoleConsts.CREATOR})
    @PostMapping("/createVideo")
    public APIResponse createVideo(@Valid ArticleCreateDTO articleCreateDTO, BindingResult bindingResult) {
        Article article = new Article();
        BeanUtils.copyProperties(articleCreateDTO, article);
        article.setUserId(UserUtils.getLoginUserId());
        article.setCreatedBy(UserUtils.getLoginUserId());
        article.setCategory(ArticleCategoryEnum.VIDEO.getCode());
        return APIResponse.OK(articleService.create(article));
    }

    @ApiOperation("更新文章")
    @RoleAccess({RoleConsts.CREATOR})
    @PostMapping("/update")
    public APIResponse update(@Valid ArticleUpdateDTO articleUpdateDTO, BindingResult bindingResult) {
        Article article = new Article();
        BeanUtils.copyProperties(articleUpdateDTO, article);
        article.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(articleService.update(article));
    }
    @ApiOperation("更新视频文章")
    @RoleAccess({RoleConsts.CREATOR})
    @PostMapping("/updateVideo")
    public APIResponse updateVideo(@Valid ArticleUpdateDTO articleUpdateDTO, BindingResult bindingResult) {
        Article article = new Article();
        BeanUtils.copyProperties(articleUpdateDTO, article);
        article.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(articleService.update(article));
    }

    @ApiOperation("按主键id查询文章")
    @RoleAccess({RoleConsts.CREATOR})
    @GetMapping("/selectById")
    public APIResponse<ArticleEditVO> selectById(Long id) {
        Article article = articleService.findById(id);
        ArticleEditVO articleEditVO = new ArticleEditVO();
        BeanUtils.copyProperties(article, articleEditVO);
        return APIResponse.OK(articleEditVO);
    }

    @ApiOperation("按主键id查询视频文章")
    @RoleAccess({RoleConsts.CREATOR})
    @GetMapping("/selectVideoById")
    public APIResponse<ArticleEditVideoVO> selectVideoById(Long id) {
        Article article = articleService.findById(id);
        ArticleEditVideoVO articleEditVideoVO = new ArticleEditVideoVO();
        BeanUtils.copyProperties(article, articleEditVideoVO);
        return APIResponse.OK(articleEditVideoVO);
    }

    @ApiOperation("删除文章")
    @RoleAccess({RoleConsts.CREATOR, RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        articleService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiIgnore
    @ApiOperation("分页查询文章")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<Article>> selectByPage(Article article, int pageNum, int pageSize) {
        List<Article> list = articleService.selectByPage(article, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

}