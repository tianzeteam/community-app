package com.smart.home.util.upload;

import com.smart.home.common.bean.Upload;
import com.smart.home.common.util.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author jason
 */
public class FileUpload {

    /**
     * 创建一个Upload实体对象
     *
     * @param multipartFile MultipartFile对象
     */
    public static Upload getFile(MultipartFile multipartFile) {
        if (multipartFile.getSize() == 0) {
            throw new RuntimeException(EnumFileUploadError.FILE_IS_EMPTY.getMessage());
        }
        Upload upload = new Upload();
        upload.setMime(multipartFile.getContentType());
        upload.setSize(multipartFile.getSize());
        upload.setOriginalName(multipartFile.getOriginalFilename());
        upload.setNewName(FileUpload.genFileName(multipartFile.getOriginalFilename()));
        return upload;
    }

    /**
     * 判断文件是否为支持的格式
     *
     * @param multipartFile MultipartFile对象
     * @param types         支持的文件类型数组
     */
    public static boolean isContentType(MultipartFile multipartFile, String[] types) {
        List<String> typeList = Arrays.asList(types);
        return typeList.contains(multipartFile.getContentType());
    }

    /**
     * 生成随机且唯一的文件名
     */
    public static String genFileName(String originalFilename) {
        String fileSuffix = FileUtils.getFileSuffix(originalFilename);
        return UUID.randomUUID().toString().replace("-", "") + fileSuffix;
    }

    /**
     * 获取文件的SHA1值
     */
    public static String getFileSha1(MultipartFile multipartFile) {
        if (multipartFile.getSize() == 0) {
            throw new RuntimeException(EnumFileUploadError.FILE_IS_EMPTY.getMessage());
        }
        byte[] buffer = new byte[4096];
        try (InputStream fis = multipartFile.getInputStream()) {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                sha1.update(buffer, 0, len);
            }
            BigInteger sha1Bi = new BigInteger(1, sha1.digest());
            return sha1Bi.toString(16);
        } catch (IOException | NoSuchAlgorithmException e) {
            return null;
        }
    }

}
