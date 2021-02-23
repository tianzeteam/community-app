package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.other.entity.SubjectCard;
import com.smart.home.modules.other.service.SubjectCardService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "other专题卡片接口")
@RestController
@RequestMapping("/api/pc/subjectCard")
public class SubjectCardController {

    @Autowired
    private SubjectCardService subjectCardService;

    @ApiOperation("创建专题卡片")
    @PostMapping("/create")
    public APIResponse create(SubjectCard subjectCard) {
        subjectCard.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(subjectCardService.create(subjectCard));
    }

    @ApiOperation("更新专题卡片")
    @PostMapping("/update")
    public APIResponse update(SubjectCard subjectCard) {
        subjectCard.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(subjectCardService.update(subjectCard));
    }

    @ApiOperation("删除专题卡片")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        subjectCardService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询专题卡片")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<SubjectCard>> selectByPage(SubjectCard subjectCard, int pageNum, int pageSize) {
        List<SubjectCard> list = subjectCardService.selectByPage(subjectCard, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询专题卡片")
    @GetMapping("/selectById")
    public APIResponse<SubjectCard> selectById(Long id) {
        return APIResponse.OK(subjectCardService.findById(id));
    }

}