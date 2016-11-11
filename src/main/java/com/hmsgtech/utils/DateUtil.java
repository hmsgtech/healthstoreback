package com.hmsgtech.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	private static Date date_1000 = null;

	static {
		try {
			date_1000 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1000-01-01 00:00:00");
		} catch (ParseException e) {
		}
	}

	public static Date addMinutes(int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

	public static Date get1000Date() {
		return date_1000;
	}

	public static Date parse(String timeStr) {
		return parse("yyyy-MM-dd HH:mm:ss", timeStr);
	}

	public static Date parse(String pattern, String timeStr) {
		try {
			return new SimpleDateFormat(pattern).parse(timeStr);
		} catch (ParseException e) {
		}
		return null;
	}

	public static String format(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static String formatYYYYMMDD(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String formatHHmm(Date date) {
		return new SimpleDateFormat("HH:mm").format(date);
	}

	public static int getAge(Date birthDate) {
		if (birthDate == null) {
			return 0;
		}
		int age = 0;
		Date now = new Date();
		SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
		SimpleDateFormat format_M = new SimpleDateFormat("MM");
		SimpleDateFormat format_d = new SimpleDateFormat("dd");

		Integer birth_year = Integer.parseInt(format_y.format(birthDate));
		Integer this_year = Integer.parseInt(format_y.format(now));

		Integer birth_month = Integer.parseInt(format_M.format(birthDate));
		Integer this_month = Integer.parseInt(format_M.format(now));

		Integer birth_day = Integer.parseInt(format_d.format(birthDate));
		Integer this_day = Integer.parseInt(format_d.format(now));

		age = this_year - birth_year;

		// 如果未到出生月份，则age - 1
		if (this_month < birth_month) {
			age -= 1;
		} else if (this_month == birth_month) {
			if (this_day < birth_day) {
				age -= 1;
			}
		}
		if (age < 0) {
			age = 0;
		}

		return age;

	}

	public static int getYear() {
		return Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
	}

	public static String format(long millis) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(millis));
	}

	public static String format(String pattern, long millis) {
		return new SimpleDateFormat(pattern).format(new Date(millis));
	}

	public static String format(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 取得下一个更新统计缓存时间，即明日0点
	 * 
	 * @return
	 */
	public static Date nextTaskTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 取得当前月份
	 * 
	 * @return
	 */
	public static Date getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 取得当日0点
	 * 
	 * @return
	 */
	public static Date getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	public static int getDayWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static Calendar getCalendar(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar;
	}

	public static int getDifferDays(Date begin, Date end) {
		return (int) ((end.getTime() - begin.getTime()) / (24 * 60 * 60 * 1000));
	}

	public static int getDifferHours(Date begin, Date end) {
		return (int) ((end.getTime() - begin.getTime()) / (60 * 60 * 1000));
	}

	/**
	 * 将毫秒值转化为人性化的时间提示字符串
	 *
	 * @param millisecond
	 * @return
	 */
	public static String millisecond2String(long millisecond) {
		long time = System.currentTimeMillis() - millisecond;
		// e("time:" + time);

		if (time < 60000)
			return "刚刚";
		else if (time < 120000)
			return "1分钟前";

		// int day = (int) (time/(60000*60*24));
		Calendar calendar1 = Calendar.getInstance();
		int cal_day1 = calendar1.get(Calendar.DAY_OF_MONTH);
		int cal_month1 = calendar1.get(Calendar.MONTH);
		int cal_year1 = calendar1.get(Calendar.YEAR);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(millisecond);
		int cal_day2 = calendar2.get(Calendar.DAY_OF_MONTH);
		int cal_month2 = calendar2.get(Calendar.MONTH);
		int cal_year2 = calendar2.get(Calendar.YEAR);
		// , Locale.getDefault()
		Date date = new Date(millisecond);
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 跟当前时间不是同一年
		if (cal_year1 != cal_year2)
			return smf.format(date);
		// 跟当前时间同年不同月
		else if (cal_month1 != cal_month2) {
			smf.applyPattern("MM-dd HH:mm");
			return smf.format(date);
			// 跟当前时间是同一月，判断相差几天
		} else {
			smf.applyPattern("HH:mm");
			int day = cal_day1 - cal_day2;
			if (day == 0)
				return smf.format(date);
			else if (day == 1)
				return "昨天 " + smf.format(date);
			else if (day == 2)
				return "前天 " + smf.format(date);
		}

		smf.applyPattern("MM-dd HH:mm");
		return smf.format(date);
	}

	public static long getRemainingDays(long millisecond) {
		return (millisecond - System.currentTimeMillis()) / (1000 * 60 * 60 * 24);
	}
}
