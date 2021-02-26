package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.response.user.PcUserProfileArticleVO;
import com.smart.home.controller.pc.response.user.PcUserProfileVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.entity.UserData;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.modules.user.service.UserDataService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Api(tags = "个人主页")
@RestController
@RequestMapping("/api/pc/userProfile")
public class UserProfileController {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private ArticleService articleService;

    @ApiOperation("获取用户基本信息")
    @RoleAccess({RoleConsts.REGISTER, RoleConsts.CREATOR, RoleConsts.AUDITOR, RoleConsts.ADMIN, RoleConsts.OPERATOR})
    @GetMapping("/myBaseInfo")
    public APIResponse<PcUserProfileVO> myBaseInfo() {
        Long userId = UserUtils.getLoginUserId();
        UserAccount userAccount = userAccountService.findUserByUserId(userId);
        UserData userData = userDataService.findByUserId(userId);
        PcUserProfileVO vo = new PcUserProfileVO();
        vo.setHeadUrl(userAccount.getHeadUrl());
        vo.setNickName(userAccount.getNickName());
        vo.setRemark(userData.getRemark());
        vo.setArticleCount(articleService.countByUserIdAndState(userId, 1));
        vo.setDraftArticleCount(articleService.countByUserIdAndState(userId, 0));
        return APIResponse.OK(vo);
    }

    @ApiOperation("根据审核状态获取投稿数据-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryType", value = "查询类型：1我的投稿0我的草稿", required = true),
            @ApiImplicitParam(name = "auditState", value = "审核状态：0审核中1审核通过2审核未通过", required = true),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @RoleAccess({RoleConsts.REGISTER, RoleConsts.CREATOR, RoleConsts.AUDITOR, RoleConsts.ADMIN, RoleConsts.OPERATOR})
    @GetMapping("/myArticleInfo")
    public APIResponse<ResponsePageBean<PcUserProfileArticleVO>> myArticleInfo(Integer queryType, Integer auditState, Integer pageNum, Integer pageSize) {
        Long userId = UserUtils.getLoginUserId();
        List<Article> list = articleService.selectTitleImageCreateIimeByPage(userId, queryType, auditState, pageNum, pageSize);
        List<PcUserProfileArticleVO> resultList = BeanCopyUtils.convertListTo(list, PcUserProfileArticleVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }

}
