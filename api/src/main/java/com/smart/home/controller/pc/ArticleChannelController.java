package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.article.entity.ArticleChannel;
import com.smart.home.modules.article.service.ArticleChannelService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "article文章频道接口")
@RestController
@RequestMapping("/api/pc/articleChannel")
public class ArticleChannelController {

    @Autowired
    private ArticleChannelService articleChannelService;

    @ApiOperation("创建文章频道")
    @PostMapping("/create")
    public APIResponse create(ArticleChannel articleChannel) {
        articleChannel.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(articleChannelService.create(articleChannel));
    }

    @ApiOperation("更新文章频道")
    @PostMapping("/update")
    public APIResponse update(ArticleChannel articleChannel) {
        articleChannel.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(articleChannelService.update(articleChannel));
    }

    @ApiOperation("删除文章频道")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        articleChannelService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询文章频道")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ArticleChannel>> selectByPage(ArticleChannel articleChannel, int pageNum, int pageSize) {
        List<ArticleChannel> list = articleChannelService.selectByPage(articleChannel, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询文章频道")
    @GetMapping("/selectById")
    public APIResponse<ArticleChannel> selectById(Long id) {
        return APIResponse.OK(articleChannelService.findById(id));
    }

}