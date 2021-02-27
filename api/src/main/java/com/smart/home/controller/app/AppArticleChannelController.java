package com.smart.home.controller.app;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.article.ArticleChannelVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.article.entity.ArticleChannel;
import com.smart.home.modules.article.service.ArticleChannelService;
import com.smart.home.modules.user.service.UserArticleChannelPreferenceService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Api(tags = "首页-频道")
@RestController
@RequestMapping("/api/app/articleChannel")
public class AppArticleChannelController {

    @Autowired
    private ArticleChannelService articleChannelService;
    @Autowired
    private UserArticleChannelPreferenceService userArticleChannelPreferenceService;

    @ApiOperation("获取全部频道")
    @AnonAccess
    @GetMapping("/queryAllChannel")
    public APIResponse<List<ArticleChannelVO>> queryAllChannel() {
        List<ArticleChannel> list = articleChannelService.selectAllValid();
        List<ArticleChannelVO> resultList = BeanCopyUtils.convertListTo(list, ArticleChannelVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("添加到我的频道")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channelId", value = "频道主键id", required = true),
            @ApiImplicitParam(name = "sort", value = "排序号", required = true)

    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/addToMyChannel")
    public APIResponse addToMyChannel(Long channelId, Integer sort) {
        Long userId = UserUtils.getLoginUserId();
        userArticleChannelPreferenceService.create(userId, channelId, sort);
        return APIResponse.OK();
    }

    @ApiOperation("从我的频道移除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channelId", value = "频道主键id", required = true),

    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/removeFromMyChannel")
    public APIResponse removeFromMyChannel(Long channelId) {
        Long userId = UserUtils.getLoginUserId();
        userArticleChannelPreferenceService.delete(userId, channelId);
        return APIResponse.OK();
    }

    @ApiOperation("查询我添加的频道")
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryMyChannel")
    public APIResponse<List<ArticleChannelVO>> queryMyChannel() {
        List<ArticleChannel> list = userArticleChannelPreferenceService.queryMyChannel(UserUtils.getLoginUserId());
        List<ArticleChannelVO> resultList = BeanCopyUtils.convertListTo(list, ArticleChannelVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("查询除去我的频道后的其他频道")
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryOtherChannel")
    public APIResponse<List<ArticleChannelVO>> queryOtherChannel() {
        List<ArticleChannel> allList = articleChannelService.selectAllValid();
        List<ArticleChannel> myList = userArticleChannelPreferenceService.queryMyChannel(UserUtils.getLoginUserId());
        List<ArticleChannel> newList = allList.stream().filter(x->{
            for (ArticleChannel articleChannel : myList) {
                if (articleChannel.getId().equals(x.getId())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
        List<ArticleChannelVO> resultList = BeanCopyUtils.convertListTo(newList, ArticleChannelVO::new);
        return APIResponse.OK(resultList);
    }

}
