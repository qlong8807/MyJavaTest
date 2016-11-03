package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import utils.DateUtils;
import utils.DateUtils.DateFormat;

public class TestDate {

	public static void main(String[] args) {
		long time =  1451577600;
		long time2 = 1454255999;
		System.out.println(longTime2String(time));
		System.out.println(longTime2String(time2));
		System.out.println(getDayTime("20160121")[0]);
		System.out.println(getDayTime("20160121")[1]);
		long t = DateUtils.parse("20160104021559", DateFormat.YYYYMMDDHHMMSS).getTime();
		System.err.println(t);
	}

	/**
	 * 获得当天的起止时间
	 * 
	 * @param day
	 *            20150720
	 * @return 2015-07-20 00:00:00 2015-07-20 23:59:59
	 */
	private static long[] getDayTime(String day) {
		if (null != day && day.length() == 8) {
			long[] time = new long[2];
			Date date = DateUtils.parse(day, DateFormat.YYYYMMDD);
			Calendar calendar = DateUtils.calendar(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			long start = calendar.getTimeInMillis();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			long end = calendar.getTimeInMillis();
			time[0] = start / 1000;
			time[1] = end / 1000;
			return time;
		}
		return null;
	}

	public static String longTime2String(long time) {
		return DateUtils.format(time, DateFormat.YY_YY_MM_DD_HH_MM_SS);
	}

	public static String getNextDay(String day) {
		Date date = DateUtils.parse(day, DateFormat.YYYYMMDD);
		Calendar calendar = DateUtils.calendar(date);
		calendar.set(Calendar.DAY_OF_YEAR,
				calendar.get(Calendar.DAY_OF_YEAR) + 1);
		String result = DateUtils.format(calendar.getTime(),
				DateFormat.YYYYMMDD);
		return result;
	}
	public static long getNextDayBeginTime(long time) {
		Date date = DateUtils.parse(time);
		Calendar calendar = DateUtils.calendar(date);
		calendar.set(Calendar.DAY_OF_YEAR,
				calendar.get(Calendar.DAY_OF_YEAR) + 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		long result = calendar.getTime().getTime();
		return result;
	}

	/**
	 * 传入yyMM获取当月的第一天的00：00：00
	 * @param dayOfMonth
	 * @return
	 */
	public static long getBeginDayOfMonth(String dayOfMonth) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
		try {
			Date date = sdf.parse(dayOfMonth);
			Calendar calendar = DateUtils.calendar(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			long time = calendar.getTimeInMillis();
			return time/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0l;
	}
	/**
	 * 传入yyMM获取当月的第一天的00：00：00
	 * @param dayOfMonth
	 * @return
	 */
	public static long getEndDayOfMonth(String dayOfMonth) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
		try {
			Date date = sdf.parse(dayOfMonth);
			Calendar calendar = DateUtils.calendar(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			long time = calendar.getTimeInMillis();
			return time/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0l;
	}
	public static List<Long[]> getMiddleDay(long begin,long end){
		List<Long[]> result = new ArrayList<Long[]>();
		long next = begin;
		while(true){
			Long[] dayTimes = new Long[2];
			dayTimes[0] = next;
			next = getNextDayBeginTime(next)/1000;
			if(next > end){
				dayTimes[1] = end;
				result.add(dayTimes);
				break;
			}else{
				dayTimes[1] = next-1;
			}
			result.add(dayTimes);
		}
		return result;
	}
}
