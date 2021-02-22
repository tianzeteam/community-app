package com.smart.home.common.util;

import java.util.Random;
import java.util.UUID;

/**
 * @author jason
 */
public class RandomUtils {

	public static final String ALL_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTER_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBER_CHARS = "0123456789";

	public static String generateNumberString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append("0123456789".charAt(random.nextInt("0123456789".length())));
		}
		return sb.toString();
	}

	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
					.charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
		}
		return sb.toString();
	}

	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
					.charAt(random.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
		}
		return sb.toString();
	}

	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));

		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	public static String toFixdLengthString(int num, int fixdlenth) {
		StringBuilder sb = new StringBuilder();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

    public static String generateState() {
		return generateNumberString(6);
    }

	/**
	 * 获取随机位数的字符串
	 * @param length 随机位数
	 */
	public static String getRandomString(int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			// 获取ascii码中的字符 数字48-57 小写65-90 大写97-122
			int range = random.nextInt(75)+48;
			range = range<97?(range<65?(range>57?114-range:range):(range>90?180-range:range)):range;
			sb.append((char)range);
		}
		return sb.toString();
	}

}