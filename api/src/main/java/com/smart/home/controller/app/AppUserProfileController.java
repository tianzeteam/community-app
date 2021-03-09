package com.smart.home.controller.app;

import com.alibaba.fastjson.JSON;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.MyFocusVO;
import com.smart.home.controller.app.response.MyFollowerVO;
import com.smart.home.controller.app.response.MyProfileVO;
import com.smart.home.controller.app.response.article.MyRootProfileArticleVO;
import com.smart.home.controller.app.response.article.MyRootProfileCommentVO;
import com.smart.home.controller.app.response.article.MyRootProfilePostReplyVO;
import com.smart.home.controller.app.response.article.MyRootProfileProductCommentVO;
import com.smart.home.controller.app.response.community.MyCollectArticleVO;
import com.smart.home.controller.app.response.community.MyDraftArticleVO;
import com.smart.home.controller.app.response.community.MyRootProfilePostVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.service.ArticleCommentReplyService;
import com.smart.home.modules.article.service.ArticleCommentService;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.entity.CommunityPostReply;
import com.smart.home.modules.community.service.CommunityPostReplyService;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.modules.product.entity.ProductComment;
import com.smart.home.modules.product.service.ProductCommentService;
import com.smart.home.modules.user.dto.MyFocusDTO;
import com.smart.home.modules.user.dto.MyFollowerDTO;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.entity.UserData;
import com.smart.home.modules.user.entity.UserTag;
import com.smart.home.modules.user.service.*;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/24
 **/
@Api(tags = "我")
@RestController
@RequestMapping("/api/app/userProfile")
public class AppUserProfileController {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private UserTagService userTagService;
    @Autowired
    private UserPrivacySettingService userPrivacySettingService;
    @Autowired
    private UserFocusService userFocusService;
    @Autowired
    private UserFollowerService userFollowerService;
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleCommentReplyService articleCommentReplyService;
    @Autowired
    private CommunityPostReplyService communityPostReplyService;
    @Autowired
    private ProductCommentService productCommentService;

    @ApiOperation("基本用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "根据用户主键ID查询，如果查当前登陆用户的不要传", required = false)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyRootProfile")
    public APIResponse<MyProfileVO> queryMyRootProfile(@RequestParam(value = "userId", required = false) Long userId) {
        if (Objects.isNull(userId)) {
            userId = UserUtils.getLoginUserId();
        }
        UserAccount userAccount = userAccountService.findUserByUserId(userId);
        UserData userData = userDataService.findByUserId(userId);
        UserTag userTag = userTagService.findByUserId(userId);
        MyProfileVO myProfileVO = new MyProfileVO();
        BeanUtils.copyProperties(userData, myProfileVO);
        myProfileVO.setHeadUrl(userAccount.getHeadUrl());
        myProfileVO.setNickName(userAccount.getNickName());
        if(!Objects.isNull(userTag)) {
            myProfileVO.setTag(userTag.getTag());
        }
        myProfileVO.setUserPrivatePrivacyList(userPrivacySettingService.queryUserSettingList(userId));
        return APIResponse.OK(myProfileVO);
    }

    @ApiOperation("发帖数据-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "根据用户主键ID查询，如果查当前登陆用户的不要传", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyRootProfilePost")
    public APIResponse<ResponsePageBean<MyRootProfilePostVO>> queryMyRootProfileData(@RequestParam(value = "userId", required = false) Long userId, int pageNum, int pageSize) {
        if (Objects.isNull(userId)) {
            userId = UserUtils.getLoginUserId();
        }
        List<CommunityPost> list = communityPostService.queryViaUserIdByPage(userId, pageNum, pageSize);
        List<MyRootProfilePostVO> resultList = BeanCopyUtils.convertListTo(list, MyRootProfilePostVO::new,(s, t)->{
            if (StringUtils.isNotBlank(s.getImages())) {
                t.setImageList(JSON.parseArray(s.getImages(), String.class));
            }
        });
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }
    @ApiOperation("评论数据-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "根据用户主键ID查询，如果查当前登陆用户的不要传", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyRootProfileComment")
    public APIResponse<ResponsePageBean<MyRootProfileCommentVO>> queryMyRootProfileComment(@RequestParam(value = "userId", required = false) Long userId, int pageNum, int pageSize) {
        if (Objects.isNull(userId)) {
            userId = UserUtils.getLoginUserId();
        }
        List<ArticleComment> list = articleCommentService.queryViaUserIdByPageWhenLogin(userId, pageNum, pageSize, UserUtils.getLoginUserId());
        List<MyRootProfileCommentVO> resultList = BeanCopyUtils.convertListTo(list, MyRootProfileCommentVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }
    @ApiOperation("回帖数据-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "根据用户主键ID查询，如果查当前登陆用户的不要传", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyRootProfileReply")
    public APIResponse<ResponsePageBean<MyRootProfilePostReplyVO>> queryMyRootProfileReply(@RequestParam(value = "userId", required = false) Long userId, int pageNum, int pageSize) {
        if (Objects.isNull(userId)) {
            userId = UserUtils.getLoginUserId();
        }
        List<CommunityPostReply> list = communityPostReplyService.queryViaUserIdByPageWhenLogin(userId, pageNum, pageSize, UserUtils.getLoginUserId());
        List<MyRootProfilePostReplyVO> resultList = BeanCopyUtils.convertListTo(list, MyRootProfilePostReplyVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }
    @ApiOperation("评价数据-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "根据用户主键ID查询，如果查当前登陆用户的不要传", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyRootProfileEvaluate")
    public APIResponse<ResponsePageBean<MyRootProfileProductCommentVO>> queryMyRootProfileEvaluate(@RequestParam(value = "userId", required = false) Long userId, int pageNum, int pageSize) {
        if (Objects.isNull(userId)) {
            userId = UserUtils.getLoginUserId();
        }
        List<ProductComment> list = productCommentService.queryViaUserIdByPageWhenLogin(userId, pageNum, pageSize, UserUtils.getLoginUserId());
        List<MyRootProfileProductCommentVO> resultList = BeanCopyUtils.convertListTo(list, MyRootProfileProductCommentVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }
    @ApiOperation("投稿数据-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "根据用户主键ID查询，如果查当前登陆用户的不要传", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyRootProfileContribute")
    public APIResponse<ResponsePageBean<MyRootProfileArticleVO>> queryMyRootProfileContribute(@RequestParam(value = "userId", required = false) Long userId, int pageNum, int pageSize) {
        if (Objects.isNull(userId)) {
            userId = UserUtils.getLoginUserId();
        }
        List<Article> list = articleService.queryViaUserIdByPageWhenLogin(userId, pageNum, pageSize, UserUtils.getLoginUserId());
        List<MyRootProfileArticleVO> resultList = BeanCopyUtils.convertListTo(list, MyRootProfileArticleVO::new,(s,t)->{
            if (StringUtils.isNotBlank(s.getBannerImages())) {
                t.setImageList(JSON.parseArray(s.getBannerImages(), String.class));
            }
        });
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }

    @ApiOperation("关注-用户点击关注按钮")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "focusUserId", value = "被关注的用户主键ID", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/focusUser")
    public APIResponse focusUser(Long focusUserId) {
        try {
            userFocusService.focusUser(focusUserId, UserUtils.getLoginUserId());
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("关注-用户点击取消关注按钮")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "focusUserId", value = "被关注的用户主键ID", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/cancelFocusUser")
    public APIResponse cancelFocusUser(Long focusUserId) {
        userFocusService.cancelFocusUser(focusUserId, UserUtils.getLoginUserId());
        return APIResponse.OK();
    }

    @ApiOperation("关注-分页查询我的关注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/myFocusByPage")
    public APIResponse<ResponsePageBean<MyFocusVO>> myFocusByPage(int pageNum, int pageSize) {
        Long userId = UserUtils.getLoginUserId();
        List<MyFocusDTO> list = userFollowerService.myFocusByPage(userId, pageNum, pageSize);
        List<MyFocusVO> resultList = BeanCopyUtils.convertListTo(list, MyFocusVO::new);
        return APIResponse.OK(ResponsePageBean.restPage(resultList));
    }

    @ApiOperation("粉丝-分页查询我的粉丝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/myFollowerByPage")
    public APIResponse<ResponsePageBean<MyFollowerVO>> myFollowerByPage(int pageNum, int pageSize) {
        Long userId = UserUtils.getLoginUserId();
        List<MyFollowerDTO> list = userFollowerService.myFollowerByPage(userId, pageNum, pageSize);
        List<MyFollowerVO> resultList = BeanCopyUtils.convertListTo(list, MyFollowerVO::new);
        return APIResponse.OK(ResponsePageBean.restPage(resultList));
    }

    @ApiOperation("收藏-分页查询我的收藏投稿")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true),
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/myCollectArticleByPage")
    public APIResponse<ResponsePageBean<MyCollectArticleVO>> myCollectArticleByPage(int pageNum, int pageSize) {
        List<Article> list = articleService.queryCollectViaUserIdByPage(UserUtils.getLoginUserId(), pageNum, pageSize);
        List<MyCollectArticleVO> resultList = BeanCopyUtils.convertListTo(list, MyCollectArticleVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }
    @ApiOperation("收藏-分页查询我的收藏帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true),
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/myCollectPostByPage")
    public APIResponse myCollectPostByPage(int pageNum, int pageSize) {
        // TODO 没原型
        return APIResponse.OK();
    }
    @ApiOperation("收藏-分页查询我的收藏产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true),
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/myCollectProductByPage")
    public APIResponse myCollectProductByPage(int pageNum, int pageSize) {
        // TODO 没原型
        return APIResponse.OK();
    }

    @ApiOperation("草稿箱-分页查询我的草稿箱")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/myDraftArticleByPage")
    public APIResponse<ResponsePageBean<MyDraftArticleVO>> myDraftArticleByPage(int pageNum, int pageSize) {
        List<Article> list = articleService.queryDraftViaUserIdByPage(UserUtils.getLoginUserId(), pageNum, pageSize);
        List<MyDraftArticleVO> resultList = BeanCopyUtils.convertListTo(list, MyDraftArticleVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }

    @ApiOperation("删除草稿")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/deleteDraftArticle")
    public APIResponse deleteDraftArticle(Long articleId) {
        articleService.deleteDraftById(articleId);
        return APIResponse.OK();
    }


}
