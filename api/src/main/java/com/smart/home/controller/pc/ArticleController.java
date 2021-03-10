package com.smart.home.controller.pc;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.article.ArticleCreateDTO;
import com.smart.home.controller.pc.request.article.ArticleUpdateDTO;
import com.smart.home.controller.pc.request.product.ProductPageSearchDTO;
import com.smart.home.controller.pc.response.article.ArticleEditVO;
import com.smart.home.controller.pc.response.article.ArticleEditVideoVO;
import com.smart.home.controller.pc.response.article.ArticleInsertProductVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.AgreementTypeEnum;
import com.smart.home.enums.ArticleCategoryEnum;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.product.entity.Product;
import com.smart.home.modules.product.service.ProductService;
import com.smart.home.modules.system.service.SysAgreementService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    @Autowired
    private ProductService productService;

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

    @ApiOperation("获取版权申明列表")
    @AnonAccess
    @GetMapping("/selectCopyrightNoticeItems")
    public APIResponse selectCopyrightNoticeItems() {
        Map<String, String> map = Maps.newHashMap();
        map.put("许署名转载", "许署名转载");
        map.put("未经授权禁止转载", "未经授权禁止转载");
        return APIResponse.OK(map);
    }

    @ApiOperation("获取插入的产品列表-分页")
    @RoleAccess({RoleConsts.REGISTER, RoleConsts.CREATOR})
    @PostMapping("/selectInsertProductByPage")
    public APIResponse<ResponsePageBean<ArticleInsertProductVO>> selectInsertProductByPage(@RequestBody ProductPageSearchDTO productPageSearchDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productPageSearchDTO, product);
        product.setOnlineFlag(YesNoEnum.YES.getCode());
        List<Product> list = productService.selectByPage(product, productPageSearchDTO.getPageNum(), productPageSearchDTO.getPageSize());
        List<ArticleInsertProductVO> resultList = BeanCopyUtils.convertListTo(list, ArticleInsertProductVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }

    @ApiOperation("投稿/保存草稿文章")
    @RoleAccess({RoleConsts.CREATOR, RoleConsts.REGISTER})
    @PostMapping("/create")
    public APIResponse create(@Valid @RequestBody ArticleCreateDTO articleCreateDTO, BindingResult bindingResult) {
        if (StringUtils.isBlank(articleCreateDTO.getCoverImage()) && CollectionUtils.isEmpty(articleCreateDTO.getBannerImagesList())) {
            return APIResponse.ERROR("封面图片和轮播图片不能同时为空");
        }
        if (StringUtils.isBlank(articleCreateDTO.getCoverImage())) {
            // 如果封面图片为空，取轮播图片第一个为封面图片
            articleCreateDTO.setCoverImage(articleCreateDTO.getBannerImagesList().get(0));
        }
        Article article = new Article();
        BeanUtils.copyProperties(articleCreateDTO, article);
        article.setUserId(UserUtils.getLoginUserId());
        article.setCreatedBy(UserUtils.getLoginUserId());
        article.setCategory(ArticleCategoryEnum.CONTENT.getCode());
        if (!CollectionUtils.isEmpty(articleCreateDTO.getBannerImagesList())) {
            article.setBannerImages(JSON.toJSONString(articleCreateDTO.getBannerImagesList()));
        }
        return processCreateArticle(articleCreateDTO, article);
    }

    @ApiOperation("投稿/保存草稿视频")
    @RoleAccess({RoleConsts.CREATOR, RoleConsts.REGISTER})
    @PostMapping("/createVideo")
    public APIResponse createVideo(@Valid @RequestBody ArticleCreateDTO articleCreateDTO, BindingResult bindingResult) {
        Article article = new Article();
        BeanUtils.copyProperties(articleCreateDTO, article);
        article.setUserId(UserUtils.getLoginUserId());
        article.setCreatedBy(UserUtils.getLoginUserId());
        article.setCategory(ArticleCategoryEnum.VIDEO.getCode());
        return processCreateArticle(articleCreateDTO, article);
    }

    @ApiOperation("更新文章")
    @RoleAccess({RoleConsts.CREATOR, RoleConsts.REGISTER})
    @PostMapping("/update")
    public APIResponse update(@Valid @RequestBody ArticleUpdateDTO articleUpdateDTO, BindingResult bindingResult) {
        Article article = new Article();
        BeanUtils.copyProperties(articleUpdateDTO, article);
        article.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(articleService.update(article));
    }
    @ApiOperation("更新视频文章")
    @RoleAccess({RoleConsts.CREATOR, RoleConsts.REGISTER})
    @PostMapping("/updateVideo")
    public APIResponse updateVideo(@Valid @RequestBody ArticleUpdateDTO articleUpdateDTO, BindingResult bindingResult) {
        Article article = new Article();
        BeanUtils.copyProperties(articleUpdateDTO, article);
        article.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(articleService.update(article));
    }

    @ApiOperation("按主键id查询文章")
    @RoleAccess({RoleConsts.CREATOR, RoleConsts.REGISTER})
    @GetMapping("/selectById")
    public APIResponse<ArticleEditVO> selectById(Long id) {
        Article article = articleService.findById(id);
        ArticleEditVO articleEditVO = new ArticleEditVO();
        BeanUtils.copyProperties(article, articleEditVO);
        return APIResponse.OK(articleEditVO);
    }

    @ApiOperation("按主键id查询视频文章")
    @RoleAccess({RoleConsts.CREATOR, RoleConsts.REGISTER})
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

    private APIResponse processCreateArticle(@RequestBody @Valid ArticleCreateDTO articleCreateDTO, Article article) {
        if (articleCreateDTO.getProductTestResultDTO() == null && articleCreateDTO.getProductId() == null) {
            return APIResponse.ERROR("产品未插入，不能插入评测");
        }
        String testResult = null;
        Integer recommendFlag = null;
        if (articleCreateDTO.getProductTestResultDTO() != null) {
            testResult = articleCreateDTO.getProductTestResultDTO().getTestResult();
            recommendFlag = articleCreateDTO.getProductTestResultDTO().getRecommendFlag();
            if (Objects.isNull(recommendFlag)) {
                return APIResponse.ERROR("评测是否推荐不能为空");
            }
            if (StringUtils.isBlank(testResult)) {
                return APIResponse.ERROR("评测结论不能为空");
            }
            if (testResult.length() > 200) {
                return APIResponse.ERROR("评测结论不能超过200字");
            }
        }
        return APIResponse.OK(articleService.create(article, articleCreateDTO.getProductId(), testResult, recommendFlag));
    }

}