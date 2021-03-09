package com.smart.home.controller.app;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.controller.app.request.ReportCreateDTO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.ReportCategoryEnum;
import com.smart.home.modules.other.service.ReportHistoryService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Api(tags = "举报")
@RestController
@RequestMapping("/api/app/report")
public class AppReportController {

    @Autowired
    private ReportHistoryService reportHistoryService;

    @ApiOperation("举报文章")
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/reportArticle")
    public APIResponse reportArticle(@Valid @RequestBody ReportCreateDTO reportCreateDTO, BindingResult bindingResult) {
        Long id = reportCreateDTO.getId();
        Long userId = UserUtils.getLoginUserId();
        String contents = reportCreateDTO.getContents();
        String images = reportCreateDTO.getImages();
        Long authorUserId = reportCreateDTO.getAuthorUserId();
        String reason = reportCreateDTO.getReason();
        try {
            reportHistoryService.create(ReportCategoryEnum.ARTICLE, id, userId, contents, images, authorUserId, reason);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("举报帖子")
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/reportCommunityPost")
    public APIResponse reportCommunityPost(@Valid @RequestBody ReportCreateDTO reportCreateDTO, BindingResult bindingResult) {
        Long id = reportCreateDTO.getId();
        Long userId = UserUtils.getLoginUserId();
        String contents = reportCreateDTO.getContents();
        String images = reportCreateDTO.getImages();
        Long authorUserId = reportCreateDTO.getAuthorUserId();
        String reason = reportCreateDTO.getReason();
        try {
            reportHistoryService.create(ReportCategoryEnum.POST, id, userId, contents, images, authorUserId, reason);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

}
