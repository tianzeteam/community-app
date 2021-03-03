package com.smart.home.common.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Upload {

    /**
     * 新生成的带UUID的文件名
     */
    private String newName;
    /**
     * 原始文件名
     */
    private String originalName;
    private String mime;
    private Long size;
    private String md5;
    private String sha1;
    /**
     * 存储类型：cos
     */
    private String storeType;

    private String bucketName;

    private Long createdBy;

    private String url;

    private String category;

}
