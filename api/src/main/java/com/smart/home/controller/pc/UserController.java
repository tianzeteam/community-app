package com.smart.home.controller.pc;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.request.CommunityUserBanDTO;
import com.smart.home.controller.pc.response.user.UserAdminContentVO;
import com.smart.home.controller.pc.response.user.UserAdminVO;
import com.smart.home.controller.pc.response.user.UserDataAdminVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.service.ArticleCommentService;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.entity.CommunityPostReply;
import com.smart.home.modules.community.service.CommunityPostReplyService;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.modules.product.entity.ProductComment;
import com.smart.home.modules.product.service.ProductCommentService;
import com.smart.home.modules.user.entity.UserCommunityAuth;
import com.smart.home.modules.user.entity.UserData;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.modules.user.service.UserCommunityAuthService;
import com.smart.home.modules.user.service.UserDataService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/3/2
 **/
@Api(tags = "管理后台-用户管理")
@RestController
@RequestMapping("/api/pc/user")
public class UserController {

    @Autowired
    private UserDataService userDataService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ProductCommentService productCommentService;
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityPostReplyService communityPostReplyService;
    @Autowired
    private UserCommunityAuthService userCommunityAuthService;

    @ApiOperation("根据用户id或者昵称搜索用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户主键id", required = false),
            @ApiImplicitParam(name = "nickName", value = "用户昵称", required = false)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/selectByIdOrNickname")
    public APIResponse<UserAdminVO> selectByIdOrNickname(Long userId, String nickName) {
        if(StringUtils.isBlank(nickName)) {
            nickName = null;
        }
        UserData userData = userDataService.selectByIdOrNickname(userId, nickName);
        if (Objects.isNull(userData)) {
            return APIResponse.ERROR("没有找到相关用户");
        }
        UserAdminVO userAdminVO = new UserAdminVO();
        BeanUtils.copyProperties(userData, userAdminVO);
        UserDataAdminVO userDataAdminVO = new UserDataAdminVO();
        BeanUtils.copyProperties(userData, userDataAdminVO);
        userAdminVO.setUserDataAdminVO(userDataAdminVO);
        userAdminVO.setUserCurrentStatus("正常");
        if (YesNoEnum.YES.equals(userData.getSpeakFlag())) {
            userAdminVO.setUserCurrentStatus("禁言");
        }
        if (YesNoEnum.YES.equals(userData.getBlackFlag())) {
            userAdminVO.setUserCurrentStatus("封禁");
        }
        return APIResponse.OK(userAdminVO);
    }

    @ApiOperation("根据用户id搜索用户的评论/评价/发帖回帖-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户主键id", required = true),
            @ApiImplicitParam(name = "queryType", value = "查询类型：0文章评论1产品评价2发帖3回帖", required = true),
            @ApiImplicitParam(name = "pageNum", value = "", required = true),
            @ApiImplicitParam(name = "pageSize", value = "", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/selectContentByType")
    public APIResponse<ResponsePageBean<UserAdminContentVO>> selectContentByType(Long userId, Integer queryType, int pageNum, int pageSize) {
        if (queryType.intValue() == 0) {
            ArticleComment articleComment = new ArticleComment();
            articleComment.setUserId(userId);
            List<ArticleComment> list = articleCommentService.selectByPage(articleComment, pageNum, pageSize);
            List<UserAdminContentVO> resultList = BeanCopyUtils.convertListTo(list, UserAdminContentVO::new);
            return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
        }
        if (queryType.intValue() == 1) {
            ProductComment productComment = new ProductComment();
            productComment.setUserId(userId);
            List<ProductComment> list = productCommentService.selectByPage(productComment, pageNum, pageSize);
            List<UserAdminContentVO> resultList = BeanCopyUtils.convertListTo(list, UserAdminContentVO::new, (s,t)->{
                t.setContents(s.getDetails());
            });
            return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
        }
        if (queryType.intValue() == 2) {
            CommunityPost communityPost = new CommunityPost();
            communityPost.setUserId(userId);
            List<CommunityPost> list = communityPostService.selectByPage(communityPost, pageNum, pageSize);
            List<UserAdminContentVO> resultList = BeanCopyUtils.convertListTo(list, UserAdminContentVO::new, (s,t)->{
                t.setImageList(JSON.parseArray(s.getImages(), String.class));
            });
            return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
        }
        if (queryType.intValue() == 3) {
            CommunityPostReply communityPostReply = new CommunityPostReply();
            communityPostReply.setUserId(userId);
            List<CommunityPostReply> list = communityPostReplyService.selectByPage(communityPostReply, pageNum, pageSize);
            List<UserAdminContentVO> resultList = BeanCopyUtils.convertListTo(list, UserAdminContentVO::new);
            return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
        }
        return APIResponse.ERROR("非法的queryType");
    }

    @ApiOperation("封禁用户-支持批量")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/banUser")
    public APIResponse banUser(@Valid @RequestBody List<CommunityUserBanDTO> communityUserBanDTOList, BindingResult bindingResult) {
        Long createdBy = UserUtils.getLoginUserId();
        for (CommunityUserBanDTO communityUserBanDTO : communityUserBanDTOList) {
            Long userId = communityUserBanDTO.getUserId();
            UserCommunityAuth userCommunityAuth = new UserCommunityAuth();
            userCommunityAuth.setCreatedBy(createdBy);
            userCommunityAuth.setCreatedTime(new Date());
            userCommunityAuth.setBlackFlag(YesNoEnum.YES.getCode());
            userCommunityAuth.setEffectiveStartDate(communityUserBanDTO.getEffectiveStartDate());
            userCommunityAuth.setEffectiveEndDate(communityUserBanDTO.getEffectiveEndDate());
            userCommunityAuth.setUserId(userId);
            userCommunityAuth.setRemark(Joiner.on(",").join(communityUserBanDTO.getReasonList()));
            userCommunityAuthService.createOrUpdate(userCommunityAuth);
            // 封禁后踢出登陆状态
            userAccountService.doLogout(userId);
        }
        return APIResponse.OK();
    }

    @ApiOperation("解封用户-支持批量")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/releaseUser")
    public APIResponse banUser(@RequestBody IdListBean idListBean) {
        userCommunityAuthService.releaseBlack(idListBean.getIdList());
        return APIResponse.OK();
    }

}
