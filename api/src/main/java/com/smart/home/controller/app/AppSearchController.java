package com.smart.home.controller.app;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.enums.EsSaveTypeEnum;
import com.smart.home.es.bean.ArticleBean;
import com.smart.home.es.bean.CommunityPostBean;
import com.smart.home.es.bean.ProductCommentBean;
import com.smart.home.es.common.EsConstant;
import com.smart.home.es.dto.EsSearchDTO;
import com.smart.home.es.dto.NameCountDTO;
import com.smart.home.es.service.EsCommonService;
import com.smart.home.es.service.SearchEsService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jason
 * @date 2021/3/14
 **/
@Slf4j
@Api(tags = "搜索")
@RestController
@RequestMapping("/api/app/search")
public class AppSearchController {

    @Autowired
    private SearchEsService searchEsService;
    @Autowired
    private EsCommonService esCommonService;


    @ApiOperation("搜索前N位的热词")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "搜索多少位？", required = true)
    })
    @AnonAccess
    @GetMapping("/hotKeys")
    public APIResponse hotKeys(@RequestParam(required = true) Integer size) {
        List<NameCountDTO> list = searchEsService.findHotKeyword(size);
        List<String> resultList = new ArrayList<>();
        for (NameCountDTO nameCountDTO : list) {
            resultList.add(nameCountDTO.getName());
        }
        return APIResponse.OK(resultList);
    }

    @ApiOperation("综合搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contents", value = "文案", required = true)
    })
    @AnonAccess
    @GetMapping("/multiple")
    public APIResponse multiple(String contents) {
        log.info("multiple综合搜索:{}", contents);
        searchEsService.saveKeyword(contents);
        Map<String, Object> multiple = searchEsService.multiple(contents);
        return APIResponse.OK(multiple);
    }

    @ApiOperation("综合搜索-更多")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contents", value = "文案", required = true),
            @ApiImplicitParam(name = "saveType", value = "搜索模块类型,1文章，2帖子，3产品，4产品评价", required = true),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @AnonAccess
    @GetMapping("/multipleMore")
    public APIResponse multipleMore(String contents, int saveType, int pageNum, int pageSize) {

        EsSaveTypeEnum esSaveTypeEnum = EsSaveTypeEnum.saveTypeEnumByType(saveType);
        EsSearchDTO esSearchDTO = new EsSearchDTO();
        esSearchDTO.setFrom(pageNum);
        esSearchDTO.setSize(pageSize);
        esSearchDTO.setContents(contents);
        esSearchDTO.setSaveType(saveType);
        switch (esSaveTypeEnum){
            case COMMUNITY_POST:
                List<CommunityPostBean> search = esCommonService.search(EsConstant.communityPostIndex, esSearchDTO, CommunityPostBean.class);
                return APIResponse.OK(search);
            case ARTICLE:
                List<ArticleBean> searchArticle = esCommonService.search(EsConstant.articleIndex, esSearchDTO, ArticleBean.class);
                return APIResponse.OK(searchArticle);
            case PRODUCT_COMMENT:
                List<ProductCommentBean> searchPC = esCommonService.search(EsConstant.articleIndex, esSearchDTO, ProductCommentBean.class);
                return APIResponse.OK(searchPC);
        }
        return APIResponse.ERROR("type错误");

    }

    @ApiOperation("关注人已发布搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @GetMapping("/focus/news")
    public APIResponse focusNews(Integer pageNum, Integer pageSize) {
        Long loginUserId = UserUtils.getLoginUserId();
        List<Object> objects = searchEsService.focusNewsSearch(loginUserId, pageNum, pageSize);
        return APIResponse.OK(objects);
    }


}
