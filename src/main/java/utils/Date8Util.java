package utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 *     ZoneId: 时区ID，用来确定Instant和LocalDateTime互相转换的规则
 *     Instant: 用来表示时间线上的一个点
 *     LocalDate: 表示没有时区的日期, LocalDate是不可变并且线程安全的
 *     LocalTime: 表示没有时区的时间, LocalTime是不可变并且线程安全的
 *     LocalDateTime: 表示没有时区的日期时间, LocalDateTime是不可变并且线程安全的
 *     Clock: 用于访问当前时刻、日期、时间，用到时区
 *     Duration: 用秒和纳秒表示时间的数量
 */
public class Date8Util {

    public static String getCurrentDay(){
        return LocalDate.now().toString();
    }


    public static LocalDate parse(int year, int month, int day) {
        LocalDate.parse("2019-06-23");
        return LocalDate.of(year, month, day);
    }

    public static String getTomorrowDay(){
        return LocalDate.now().plusDays(1).toString();
    }
    /**
     * 上月今天
     */
    public static String getLastMonthToday(){
        return LocalDate.now().minus(1, ChronoUnit.MONTHS).toString();
    }

    /**
     * 获取星期几
     * @param dayString 2019-06-23
     */
    public static int getDayOfWeek(String dayString) {
        DayOfWeek dayOfWeek = LocalDate.parse(dayString).getDayOfWeek();
        return dayOfWeek.getValue();
    }
    /**
     * 判断是不是闰年
     */
    public static boolean isLeapYear() {
        return LocalDate.now().isLeapYear();
    }

    /**
     * a 是否在b之前
     * @param aDayString 2019-06-23
     * @param bDayString 2019-06-23
     * @return boolean
     */
    public static boolean before(String aDayString, String bDayString) {
        return LocalDate.parse(aDayString).isBefore(LocalDate.parse(bDayString));
    }

    public static long bewteen(String initDay, String finalDay) {
        return ChronoUnit.DAYS.between(LocalDate.parse(initDay), LocalDate.parse(finalDay));
    }

    public static String getThisMonthFirstDay() {
        //return LocalDate.now().withDayOfMonth(1).toString();
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString();
    }

    public static boolean isBirthDay(String dayString) {
        LocalDate birthday = LocalDate.parse(dayString);
        MonthDay monthDay = MonthDay.of(birthday.getMonth(), birthday.getDayOfMonth());
        MonthDay today = MonthDay.now();
        return today.equals(monthDay);
    }
    public static String getCurrentTime(){
        return LocalTime.now().toString();
    }

    public static String parseTime(int hour, int min, int sec) {
        return LocalTime.of(hour, min, sec).toString();
    }
    public static int getCurrentHour(){
        return LocalTime.now().getHour();
    }

    public static Instant dateToInstant(Date date) {
        return date.toInstant();
    }

    public static Date instantToDate(Instant instant) {
        return Date.from(instant);
    }

    public static LocalDateTime dateToLocal(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date localToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date localToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取一个小时后
     */
    public static String getNextHour(){
        return LocalTime.now().plusHours(1).toString();
    }

    /**
     * 获取今天凌晨
     */
    public static String getTodayBegin() {
        return LocalTime.MIN.toString();
    }

    /**
     * 获取当前日期时间
     */
    public static String getNow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //String currentTime = dateTimeFormatter.format(LocalDateTime.now());
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    /**
     * 获取5天后
     * @return
     */
    public static String getNext5Day(){
        return LocalDateTime.now().plus(Period.ofDays(5)).toString();
    }


    public static void main(String[] args) {
        System.out.println(Date8Util.getThisMonthFirstDay());
        System.out.println(Date8Util.getNextHour());
        System.out.println(Date8Util.getTodayBegin());
        System.out.println(Date8Util.getNow());
        System.out.println(Date8Util.getNext5Day());
    }
}
