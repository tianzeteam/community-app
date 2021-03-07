package com.smart.home.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	public static String getCurrentTimestamp() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}

	public static String getDateString(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	public static String getDateTimeString(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	public static String getPreDateString(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(cal.getTime());
	}

	public static Date getPreDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	public static long getDelayTimes(String expireTime) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = format.parse(expireTime);
			return date.getTime() - System.currentTimeMillis();
		} catch (ParseException e) {
			return 60000L;
		}
	}
	
	/**
	 * 看看两个日期是否是同一天的
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean hasCrocessDay(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		int day1 = cal1.get(Calendar.DAY_OF_MONTH);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		return day1 != day2;
	}
	
	/**
	 * input 2016-10-18
	 * output 2016-10-18 23:59:59 999
	 * @param date
	 * @return
	 */
	public static Date getLastDateOfTheDay(Date date) {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
		todayEnd.set(Calendar.MINUTE, 59);  
		todayEnd.set(Calendar.SECOND, 59);  
		todayEnd.set(Calendar.MILLISECOND, 000);  
		return todayEnd.getTime();
	}
	
	public static Date getEarliestDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		cal.set(Calendar.MILLISECOND, 000);  
		return cal.getTime();
	}

	public static Date getEarliestDateOfCurrentMonth() {
		try {
			String thisMonthFirstDay = DateUtils.getCurrentMonthFirstDay();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return DateUtils.getEarliestDate(dateFormat.parse(thisMonthFirstDay));
		} catch (Exception e) {
			logger.error(ExceptionUtil.getStackTrace(e));
		}
		return null;
	}

	public static Date getEarliestDateOfPreMonth() {
		try {
			String thisMonthFirstDay = DateUtils.getPreMonthFirstDay();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return DateUtils.getEarliestDate(dateFormat.parse(thisMonthFirstDay));
		} catch (Exception e) {
			logger.error(ExceptionUtil.getStackTrace(e));
		}
		return null;
	}

	public static Date getLastDateOfCurrentMonth() {
		try {
			String thisMonthLastDay = DateUtils.getCurrentMonthLastDay();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return DateUtils.getLastDateOfTheDay(dateFormat.parse(thisMonthLastDay));
		} catch (Exception e) {
			logger.error(ExceptionUtil.getStackTrace(e));
		}
		return null;
	}

	public static Date getLastDateOfPreMonth() {
		try {
			String thisMonthLastDay = DateUtils.getPreMonthLastDay();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return DateUtils.getLastDateOfTheDay(dateFormat.parse(thisMonthLastDay));
		} catch (Exception e) {
			logger.error(ExceptionUtil.getStackTrace(e));
		}
		return null;
	}
	
	public static Date getYesterday() {
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 获取两个日期之间的天数
	 * @param before
	 * @param after
	 * @return
	 */
	private static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 计算相差多少小时
	 * @param before
	 * @param after
	 * @return
	 */
	private static double getDistancHoureOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) % (1000 * 60 * 60 * 24) / (1000 * 60 * 60);
	}

	/**
	 * 获取本周的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_WEEK, 2);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return cal.getTime();
	}

	/**
	 * 获取当前周最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();

		try {
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_WEEK, 2);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 6);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return cal.getTime();
	}

		/**
         * 获取当前月第一天
         * @return
         */
	public static String getCurrentMonthFirstDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(cale.getTime());
	}

	/**
	 * 获取当前月最后一天
	 * @return
	 */
	public static String getCurrentMonthLastDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return format.format(cale.getTime());
	}

	/**
	 * 获取当前月份第一天的日期 * @return 格式化后的日期
	 */
	public static String getFirstDayOfMonthByDate(Date date) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return myFormatter.format(cal.getTime());
	}

	/**
	 * 获取当前月份最后一天 * @return 格式化的日期
	 */
	public static String getMaxDayOfMonthByDate(Date date) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		//主要就是这个roll方法
		cal.roll(Calendar.DATE, -1);
		return myFormatter.format(cal.getTime());
	}

	/**
	 * 根据时间获得对应得周 {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
	 * @param date
	 * @return
	 */
	public static String dateToWeek(Date date) {
		String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDays[w];
	}

	public static String dateToWeekNumber(Date date) {
		String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDays[w];
	}

	/**
	 * 获取上个月第一天
	 * @return
	 */
	public static String getPreMonthFirstDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		//设置为1号,当前日期既为本月第一天
		cal.set(Calendar.DAY_OF_MONTH,1);
		return format.format(cal.getTime());
	}

	/**
	 * 获取上个月最后一天
	 * @return
	 */
	public static String getPreMonthLastDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		//设置为1号,当前日期既为本月第一天
		cale.set(Calendar.DAY_OF_MONTH,0);
		return format.format(cale.getTime());
	}

	/**
	 * 获取上周周一（第一天是周一）
	 *
	 * @return
	 */
	public static Date getPreviousWeekMonday() {
		Calendar cal = Calendar.getInstance();
		// 将每周第一天设为星期一，默认是星期天
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.DATE, -1 * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取上周周日（第一天是周一）
	 * @return
	 */
	public static Date getPreviousWeekSunday() {
		Calendar cal = Calendar.getInstance();
		//将每周第一天设为星期一，默认是星期天
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.DATE, -1*7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 根据给定的时间获取在当月的第几周
	 * @param date
	 * @return
	 */
	public static int getWeekSequenceInMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	public static void main(String[] args) {
		System.out.println(getPreDate());
	}
}
