package com.smart.home.controller.app;

import cn.hutool.core.collection.CollUtil;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.article.ArticleChannelVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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
        try {
            userArticleChannelPreferenceService.create(userId, channelId, sort);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("更新我的频道")
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/updateMyChannel")
    public APIResponse removeFromMyChannel(@RequestBody IdListBean idListBean) {
        Long userId = UserUtils.getLoginUserId();
        userArticleChannelPreferenceService.deleteAndInsert(userId, idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("查询我添加的频道")
    @GetMapping("/queryMyChannel")
    public APIResponse<List<ArticleChannelVO>> queryMyChannel() {
        Long userId = UserUtils.getLoginUserId();
        if(userId > 0) {
            List<ArticleChannel> list = userArticleChannelPreferenceService.queryMyChannel(userId);
            if (CollUtil.isEmpty(list)) {
                // 如果是空的，默认把所有评到给用户
                list = articleChannelService.selectAllValid();
                for (ArticleChannel articleChannel : list) {
                    userArticleChannelPreferenceService.create(userId, Long.valueOf(articleChannel.getId()), articleChannel.getSort());
                }
            }
            List<ArticleChannelVO> resultList = BeanCopyUtils.convertListTo(list, ArticleChannelVO::new);
            // id = 1， 说明是推荐的，app端不需要返回
            resultList = resultList.stream().filter(x->{return !x.getId().equals(1);}).collect(Collectors.toList());
            return APIResponse.OK(resultList);
        } else {
            return APIResponse.OK(Collections.EMPTY_LIST);
        }
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
