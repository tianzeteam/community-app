package com.smart.home.common.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author jason
 */
public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private static final int CACHE_SIZE = 1024;

    /**
     * 获取文件后缀名
     */
    public static String getFileSuffix(String fileName) {
        if(!fileName.isEmpty()){
            int lastIndexOf = fileName.lastIndexOf(".");
            return fileName.substring(lastIndexOf);
        }
        return "";
    }

    public static String getFileNameFromUrl(String url) {
        int lastIndexOf = url.lastIndexOf("/");
        return url.substring(lastIndexOf+1);
    }

    /**
     * 把内容写到目标文件
     * @param targetFile 目标文件
     * @param content 文件内容
     */
    public static void write(File targetFile, String content) {
        try {
            org.apache.commons.io.FileUtils.write(targetFile, content, Charset.forName("UTF-8"));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 文件转成base64
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String encodeFileToBase64(String filePath) throws Exception {
        byte[] bytes = fileToByteArray(filePath);
        return new String(Base64.encodeBase64(bytes));
    }

    /**
     * 把base64类型的文件字符串转换并输出到目的文件
     * @param base64FileString
     * @param targetFilePath
     * @throws Exception
     */
    public static void decodeBase64ToFile(String base64FileString, String targetFilePath) throws Exception {
        byte[] bytes = Base64.decodeBase64(base64FileString.getBytes());
        byteArrayToFile(bytes, targetFilePath);
    }

    /**
     * 读取文件为byte数组
     * @param filePath
     * @return
     * @throws Exception
     */
    public static byte[] fileToByteArray(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            readFileData(in, out);
            data = out.toByteArray();
        }
        return data;
    }

    /**
     * byte数组转换为文件落地
     * @param bytes
     * @param filePath
     * @throws Exception
     */
    public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);
        readFileData(in, out);
    }

    /**
     * 删除文件夹
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            //删除完里面所有内容
            delAllFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            //删除空文件夹
            myFilePath.delete();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 删除指定文件夹下的所有文件
     * @param path
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                //先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                //再删除空文件夹
                delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 把一个流变成UTF-8字符串
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return sb.toString();
    }

    /**
     * 把字符串变成输入流
     * @param sInputString
     * @return
     */
    public static InputStream getStringStream(String sInputString){
        if (sInputString != null && !sInputString.trim().equals("")){
            try{
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
        return null;
    }

    private static void readFileData(InputStream in, OutputStream out) throws IOException {
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = in.read(cache)) != -1) {
            out.write(cache, 0, nRead);
            out.flush();
        }
        out.close();
        in.close();
    }

}
