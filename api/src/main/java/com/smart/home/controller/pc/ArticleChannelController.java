package com.smart.home.controller.pc;

import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.article.ArticleChannelCreateDTO;
import com.smart.home.controller.pc.request.article.ArticleChannelUpdateDTO;
import com.smart.home.controller.pc.response.article.ArticleChannelSelectVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.article.entity.ArticleChannel;
import com.smart.home.modules.article.service.ArticleChannelService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "配置中心-频道配置")
@RestController
@RequestMapping("/api/pc/articleChannel")
public class ArticleChannelController {

    @Autowired
    private ArticleChannelService articleChannelService;

    @ApiOperation("创建文章频道")
    @PostMapping("/create")
    public APIResponse create(@Valid ArticleChannelCreateDTO articleChannelCreateDTO, BindingResult bindingResult) {
        ArticleChannel articleChannel = new ArticleChannel();
        BeanUtils.copyProperties(articleChannelCreateDTO, articleChannel);
        articleChannel.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(articleChannelService.create(articleChannel));
    }

    @ApiOperation("更新文章频道")
    @PostMapping("/update")
    public APIResponse update(@Valid ArticleChannelUpdateDTO articleChannelUpdateDTO, BindingResult bindingResult) {
        ArticleChannel articleChannel = new ArticleChannel();
        BeanUtils.copyProperties(articleChannelUpdateDTO, articleChannel);
        articleChannel.setUpdatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(articleChannelService.update(articleChannel));
    }

    @ApiOperation("删除文章频道")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        articleChannelService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询文章频道")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "按名称查询", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ArticleChannel>> selectByPage(String title, int pageNum, int pageSize) {
        ArticleChannel articleChannel = new ArticleChannel();
        articleChannel.setTitle(title);
        List<ArticleChannel> list = articleChannelService.selectByPage(articleChannel, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询文章频道")
    @GetMapping("/selectById")
    public APIResponse<ArticleChannel> selectById(Long id) {
        return APIResponse.OK(articleChannelService.findById(id));
    }

    @ApiOperation("下拉选择频道")
    @GetMapping("/selectSelectItems")
    public APIResponse<List<ArticleChannelSelectVO>> selectSelectItems() {
        List<ArticleChannel> list = articleChannelService.selectAllValid();
        List<ArticleChannelSelectVO> resultList = BeanCopyUtils.convertListTo(list, ArticleChannelSelectVO::new);
        return APIResponse.OK(resultList);
    }

}