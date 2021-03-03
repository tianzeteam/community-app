package com.smart.home.cloud.qcloud.cos;

import com.google.common.base.Joiner;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.smart.home.common.bean.Upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jason
 * @date 2021/3/3
 **/
public class CosUtil {

    private static COSClient cosClient = null;
    public static final String APP_ID = "1305077657";

    public static synchronized void intiClient() {
        if (cosClient == null) {
            // 1 初始化用户身份信息（secretId, secretKey）。
            String secretId = "AKIDwKTFnCEYWHrEOKuIZbO1TfdhZDk7g5g2";
            String secretKey = "84qNf5GApJ8j31mxtYziCPM7XicBW7TZ";
            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
            // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
            Region region = new Region("ap-nanjing");
            ClientConfig clientConfig = new ClientConfig(region);
            // 3 生成 cos 客户端。
            cosClient = new COSClient(cred, clientConfig);
            // 查询存储桶列表， 没有就生成
            List<String> bucketNameList = new ArrayList<>();
            List<Bucket> buckets = cosClient.listBuckets();
            for (Bucket bucketElement : buckets) {
                bucketNameList.add(bucketElement.getName());
            }
            if (!bucketNameList.contains("image")) {
                createBucket("image");
            }
            if (!bucketNameList.contains("video")) {
                createBucket("video");
            }
        }
    }

    private static boolean createBucket(String bucketName) {
        String bucket = Joiner.on("-").join(bucketName, APP_ID);
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
        // 设置 bucket 的权限为 Private(私有读写), 其他可选有公有读私有写, 公有读写
        createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        try {
            cosClient.createBucket(createBucketRequest);
            return true;
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }
        return false;
    }

    /**
     * 指定要上传的文件，本方法适用200M以下文件
     */
    public static String uploadFile(InputStream inputStream, Upload upload) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        //文件的大小
        objectMetadata.setContentLength(upload.getSize());
        //文件的类型
        objectMetadata.setContentType(upload.getMime());
        // 指定要上传到的存储桶
        String bucketName = Joiner.on("-").join(upload.getBucketName(), APP_ID);
        // 指定要上传到 COS 上对象键
        String key = upload.getNewName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return putObjectResult.getContentMd5();
    }

    public static void downloadFile() throws IOException {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = "examplebucket-1250000000";
        String key = "exampleobject";
        // 方法1 获取下载输入流
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        // 下载对象的 CRC64
        String crc64Ecma = cosObject.getObjectMetadata().getCrc64Ecma();
        // 关闭输入流
        cosObjectInput.close();
        // 方法2 下载文件到本地
        String outputFilePath = "exampleobject";
        File downFile = new File(outputFilePath);
        getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
    }

    public static void deleteFile() {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = "examplebucket-1250000000";
        String key = "exampleobject";
        cosClient.deleteObject(bucketName, key);
    }

    public static void closeClient() {
        // 关闭客户端(关闭后台线程)
        cosClient.shutdown();
    }

    public static void main(String[] args) {
        CosUtil.intiClient();
    }

}
