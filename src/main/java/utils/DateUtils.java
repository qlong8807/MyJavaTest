package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日期工具类<br>
 * <h>约定：</h> <li>所有时间单位为秒</li>
 * 
 * @author lgw
 * 
 */
public class DateUtils {
	public static Date parse(long date) {
		return new Date(date * 1000);
	}

	public static Date parse(String dateText, DateFormat dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				dateFormatCache.get(dateFormat.ordinal()));
		if (sdf != null)
			try {
				return sdf.parse(dateText);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static Calendar calendar(int year, int month, int day) {
		return calendar(year, month, day, 0, 0, 0);
	}

	public static Calendar calendar(int year, int month, int day, int hour, int minute,
			int second) {
		Calendar calendar = Calendar.getInstance();
		if ((hour | minute | second) != 0)
			calendar.set(year, month - 1, day, hour, minute, second);
		else
			calendar.set(year, month - 1, day);
		return calendar;
	}

	public static Calendar calendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	public static Calendar calendar(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time * 1000);
		return calendar;
	}
	public static Calendar current(){
		return Calendar.getInstance();
	}
	/**
	 * 只支持同一年
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static int differDay(Calendar beginTime , Calendar endTime){
		return Math.abs(beginTime.get(Calendar.DAY_OF_YEAR) -endTime.get(Calendar.DAY_OF_YEAR));
	}
	public static void main(String[] args) {
		Calendar beginTime = DateUtils.calendar(2013, 3, 10);
		Calendar endTime = DateUtils.calendar(2014, 3, 25);
		System.err.println(differDay(beginTime, endTime));
	}

	public static String format(Date date, DateFormat dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				dateFormatCache.get(dateFormat.ordinal()));
		return sdf.format(date);
	}

	public static String format(long date, DateFormat dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				dateFormatCache.get(dateFormat.ordinal()));
		return sdf.format(DateUtils.parse(date));
	}

	static Map<Integer, String> dateFormatCache = new ConcurrentHashMap<Integer, String>();
	static {
		dateFormatCache.put(0, "yyyy-MM-dd HH:mm:ss");
		dateFormatCache.put(1, "yyyy-MM-dd");
		dateFormatCache.put(2, "HH:mm:ss");
		dateFormatCache.put(3, "yyyy");
		dateFormatCache.put(4, "yyyyMM");
		dateFormatCache.put(5, "yyyyMMdd");
		dateFormatCache.put(6, "yyyyMMddHH");
		dateFormatCache.put(7, "yyyyMMddHHmm");
		dateFormatCache.put(8, "yyyyMMddHHmmss");
		dateFormatCache.put(9, "yyMM");
		dateFormatCache.put(10, "yyMMddHHmmss");
	}

	public enum DateFormat {
		/**
		 * Example 2011-11-03 18:00:00
		 */
		YY_YY_MM_DD_HH_MM_SS,
		/**
		 * Example 2011-11-03
		 */
		YYYY_MM_DD,
		/**
		 * Example 18:00:00
		 */
		HH_MM_SS,
		/**
		 * Example 2011
		 */
		YYYY,
		/**
		 * Example 201111
		 */
		YYYYMM,
		/**
		 * Example 20111103
		 */
		YYYYMMDD,
		/**
		 * Example 20111103
		 */
		YYYYMMDDHH,
		/**
		 * Example 2011110310
		 */
		YYYYMMDDHHMM,
		/**
		 * Example 201111031000
		 */
		YYYYMMDDHHMMSS,
		/**Example 1401*/
		YYMM,
		/**Example 140101120000*/
		YYMMDDHHMMSS,
		MMDDHHMMSS
	}
}
