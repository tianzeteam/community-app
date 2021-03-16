package com.smart.home.controller.common;

import com.smart.home.cloud.qcloud.consts.BucketConsts;
import com.smart.home.common.contants.FileStoreType;
import com.smart.home.common.contants.FileType;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.exception.RestfulRequestException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.system.service.SysFileService;
import com.smart.home.util.UserUtils;
import com.smart.home.util.upload.EnumFileUploadError;
import com.smart.home.util.upload.FileUpload;
import com.smart.home.common.bean.Upload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jason
 * @date 2020/10/27
 **/
@Api(tags = "文件上传")
@RestController
@RequestMapping("/api/common/file")
public class SysFileController {

    @Autowired
    SysFileService sysFileService;

    @ApiOperation(value = "上传图片")
    @RoleAccess({RoleConsts.REGISTER,RoleConsts.CREATOR,RoleConsts.ADMIN})
    @PostMapping("/uploadImage")
    public APIResponse addSingleImage(@RequestParam("file") MultipartFile multipartFile) {
        Upload upload = FileUpload.getFile(multipartFile);
        String[] types = {
                "image/gif",
                "image/jpg",
                "image/jpeg",
                "image/png"
        };
        if(!FileUpload.isContentType(multipartFile, types)){
            throw new RestfulRequestException(EnumFileUploadError.INVALID_FILE_TYPE.getMessage());
        }
        upload.setBucketName(BucketConsts.IMAGE);
        upload.setStoreType(FileStoreType.COS);
        upload.setCategory(FileType.IMAGE);
        return processUpload(multipartFile, upload);
    }

    @ApiOperation(value = "上传视频")
    @RoleAccess({RoleConsts.REGISTER,RoleConsts.CREATOR,RoleConsts.ADMIN})
    @PostMapping("/uploadVideo")
    public APIResponse addVideo(@RequestParam("file") MultipartFile multipartFile) {
        Upload upload = FileUpload.getFile(multipartFile);
        String[] types = {
                "video/mp4",
                "video/ogg",
                "video/x-flv",
                "video/x-msvideo",
                "video/x-ms-wmv",
                "application/vnd.rn-realmedia-vbr"
        };
        if(!FileUpload.isContentType(multipartFile, types)){
            throw new RestfulRequestException(EnumFileUploadError.INVALID_FILE_TYPE.getMessage());
        }
        upload.setBucketName(BucketConsts.VIDEO);
        upload.setStoreType(FileStoreType.COS);
        upload.setCategory(FileType.VIDEO);
        return processUpload(multipartFile, upload);
    }

    private APIResponse processUpload(MultipartFile multipartFile, Upload upload) {
        try {
            upload.setCreatedBy(UserUtils.getLoginUserId());
            sysFileService.doUpload(multipartFile.getInputStream(), upload);
            return APIResponse.OK(upload);
        } catch (Exception e) {
            return APIResponse.ERROR(e.getMessage());
        }
    }

}
