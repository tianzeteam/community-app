package com.smart.home.common.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SummaryUtils {

    /**
     * MD5算法
     *
     * @param data
     * @return
     */
    public static String md5(String data) {
        // 如果有空则返回""
        String s = data == null ? "" : data;
        try {
            // 将字符串转为字节数组
            byte[] strTemp = s.getBytes("utf-8");
            // 加密器
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            // 执行加密
            mdTemp.update(strTemp);
            // 加密结果
            byte[] byteArray = mdTemp.digest();
            return Hex.encodeHexString(byteArray);
        } catch (Exception e) {
            return null;
        }
    }

    public static String sha1HexBySalt(String plainText, String salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(Algorithm.SHA1.getValue());
            messageDigest.update(salt.getBytes());
            messageDigest.update(plainText.getBytes());
            return Hex.encodeHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public enum Algorithm {
        MD5("MD5"),
        SHA1("SHA-1");

        private String algorithm;

        Algorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getValue() {
            return this.algorithm;
        }
    }

}
