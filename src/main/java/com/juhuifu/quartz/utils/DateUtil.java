package com.juhuifu.quartz.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {
    private static final int HOUR_OF_00 = 00;
    private static final int HOUR_OF_12 = 12;
    private static final int HOUR_OF_23 = 23;
    private static final int MINUTE_OF_0 = 0;
    private static final int MINUTE_OF_59 = 59;
    private static final int SECOND_OF_0 = 0;
    private static final int MILLISECOND_OF_0 = 0;
    private static final int MILLISECOND_OF_999 = 999;
    private static final int SECOND_OF_59 = 59;
    private static final int DAY_OF_1 = 1;
    private static final int DAY_OF_31 = 31;
    private static final int DAY_OF_WEEK = 7;
    private static final int LAST_MONTH_DAYS = 30;

    /** 一分钟的豪妙数    */
    public static final long MILLISECS_PER_MINUTE = 60L * 1000;
    /**一小时的豪妙数    */
    public static final long MILLISECS_PER_HOUR = 60L * MILLISECS_PER_MINUTE;
    /** 一天的豪妙数    */
    public static final long MILLISECS_PER_DAY = 24L * MILLISECS_PER_HOUR;
    
    /**
     * 
     * dateToString(将时间转换成字符串)
     * 
     * @Title: dateToString
     * @param dataDate
     *            日期
     * @param format
     *            格式
     * @return String 返回类型
     * @throws
     */
    public static String dateToString(Date dataDate, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(dataDate);
    }

    /**
     * 
     * StringToDate(将字符串转换成日期)
     * 
     * @Title: StringToDate
     * @param dataDate
     *            日期
     * @param format
     *            格式
     * @return Date 返回类型
     * @throws
     */
    public static Date stringToDate(String dataDate, String format) {
        Date date = new Date();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            date = simpleDateFormat.parse(dataDate);
        } catch (ParseException e) {
          //  throw new SystemException("SYS_001", e);
        }
        return date;
    }

    /**
     * 将日期的时间部分转换为 00:00:00
     * 
     * @param date
     *            要格式化的日期
     * @return 格式化后的日期
     */
    public static Date dateToStartTime(Date date) {
        return DateUtil.dateToDateTime(date, HOUR_OF_00, MINUTE_OF_0, SECOND_OF_0,
                MILLISECOND_OF_0);
    }
    
    
    /**
      * dateCutSecond(将日期的秒数去掉)
      *
      * @Title: dateCutSecond
      * @param date 日期
      * @return
      * @return Date    去除秒以后的日期
      */
    public static Date dateCutSecond(Date date){
        return dateCutSecond(date, SECOND_OF_0,  MILLISECOND_OF_0);
    }
      
    private static Date dateCutSecond(Date date,int second, int milliSecond){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, milliSecond);
        return c.getTime();
    }
    
    /**
     * 将日期的时间部分转换为 12:00:00
     * 
     * @param date
     *            要格式化的日期
     * @return 格式化后的日期
     */
    public static Date dateToDateTime(Date date) {
        return DateUtil.dateToDateTime(date, HOUR_OF_12, MINUTE_OF_0, SECOND_OF_0,
                MILLISECOND_OF_0);
    }

    /**
     * 将日期的时间部分转换为某个时间
     * 
     * @param date
     *            要格式化的日期
     * @param hour
     *            初始化的时
     * @param minute
     *            初始化的分
     * @param second
     *            初始化的秒
     * @param milliSecond
     *            初始化的毫秒
     * @return 格式化后的日期
     */
    public static Date dateToDateTime(Date date, int hour, 
            int minute, int second, int milliSecond) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, milliSecond);
        return c.getTime();
    }

    /**
     * 将日期的时间部分转换为结束时间
     * 
     * @param date
     *            要格式化的日期
     * @return 格式化后的日期
     */
    public static Date dateToEndTime(Date date) {
        return DateUtil.dateToDateTime(date, HOUR_OF_23, MINUTE_OF_59, SECOND_OF_59,
                MILLISECOND_OF_999);
    }

    /**
     * getWeekOfYear(取得当前日期是多少周)
     * 
     * @Title: getWeekOfYear
     * @param date
     *            参数
     * @return int 返回类型
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setMinimalDaysInFirstWeek(DAY_OF_WEEK);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * getMaxWeekNumOfYear(得到某一年周的总数 )
     * 
     * @Title: getMaxWeekNumOfYear
     * @param year
     *            年份
     * @return int 返回类型
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, Calendar.DECEMBER, DAY_OF_31, HOUR_OF_23, MINUTE_OF_59, SECOND_OF_59);
        return getWeekOfYear(c.getTime());
    }

    /**
     * getLastDayOfWeek(得到某年某周的第一天)
     * 
     * @Title: getLastDayOfWeek
     * @param year
     *            年份
     * @param week
     *            某一周
     * @return Date 返回类型
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, DAY_OF_1);

        Calendar cal = (Calendar) c.clone();
        cal.add(Calendar.DATE, week * DAY_OF_WEEK);
        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * getLastDayOfWeek(得到某年某周的最后一天)
     * 
     * @Title: getLastDayOfWeek
     * @param year
     *            年份
     * @param week
     *            某一周
     * @return Date 返回类型
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, DAY_OF_1);

        Calendar cal = (Calendar) c.clone();
        cal.add(Calendar.DATE, week * DAY_OF_WEEK);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * getFirstDayOfWeek(取得当前日期所在周的第一天)
     * 
     * @Title: getFirstDayOfWeek
     * @param date
     *            日期
     * @return Date 返回类型
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * getLastDayOfWeek(取得当前日期所在周的最后一天)
     * 
     * @Title: getLastDayOfWeek
     * @param date
     *            日期
     * @return Date 返回类型
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }
    
    /**
      * 返回传入日期当月的第一天(忽略时间部分)
      * @param date date
      * @return 期望的日期
      */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    
    /**
      * 返回传入日期当月的最后一天(忽略时间部分)
      * @param date date
      * @return 期望的日期
      */
    public static Date getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
    
    /**
     * getDayOfBefore(取得当前日期前(后)几天的日期)
     * 
     * @Title: getDayOfBefore
     * @param date
     *            日期
     * @param offset
     *            偏移量
     * @return Date 返回类型
     */
    public static Date getDayOfOffset(Date date, int offset) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + offset);
        return c.getTime();
    }
    /**
     * 
      * getMonthOfOffset(取得当前日期前(后)几个月的日期)
      *
      * @Title: getMonthOfOffset
      * @param date 日期
      * @param offset 偏移量
      * @return Date    返回类型
     */
    public static Date getMonthOfOffset(Date date,int offset){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + offset);
        return c.getTime(); 
    }

    /**
     * 获取日期的年份
     * 
     * @param date
     *            日期
     * @return int 年份
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * 获取日期的月份
     * 
     * @param date
     *            日期
     * @return int 月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }
    
    /**
     * 获取日期的日期
     * 
     * @param date
     *            日期
     * @return int 日期
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取时间的小时
     * 
     * @param date
     *            日期
     * @return int 日期
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    /**
     * 获取时间的小时
     * 
     * @param date 日期
     * @return int 分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }
    
    /**
     * 获取时间字符串。 日期字符串格式： yyyy-MM-dd hh:mm:ss 其中： yyyy 表示4位年。 MM 表示2位月。 dd 表示2位日。
     * 
     * @return String " yyyy-MM-dd hh:mm:ss"格式的时间字符串。
     */
    public static String getSysDate() {
        return dateToString(new Date(),"yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 获取日期字符串。 日期字符串格式： yyyyMMdd 其中： yyyy 表示4位年。 MM 表示2位月。 dd 表示2位日。
     * 
     * @param date
     *            需要转化的日期。
     * @return String "yyyyMMdd"格式的日期字符串。
     */
    public static String getDate(Date date) {
        return dateToString(date,"yyyy-MM-dd");
    }

    /**
     * 获取日期字符串。 日期字符串格式： yyyyMMdd 其中： yyyy 表示4位年。 MM 表示2位月。 dd 表示2位日。
     * @return String "yyyyMMdd"格式的日期字符串。
     */
    public static String getSysDate24() {
        return dateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取日期字符串。 日期字符串格式： yyyyMM 其中： yyyy 表示4位年。 MM 表示2位月。
     * 
     * @return String "yyyyMM"格式的日期字符串。
     */
    public static String getSysDateYYYYMM() {
        return dateToString(new Date(),"yyyyMM");
    }

    /**
     * 获取日期字符串。 日期字符串格式： yyyyMMdd 其中： yyyy 表示4位年。 MM 表示2位月。 dd 表示2位日。
     * @return String "yyyyMMdd"格式的日期字符串。
     */
    public static String getSysDateYYYYMMDD() {
        return dateToString(new Date(),"yyyyMMdd");
    }
    
    /**
     * 比较两个时间大小(不比较毫秒)
     * 
     * @param timeOne 时间1
     * @param timeTwo 时间2
     * @return int 1表示大于，0表示等于，-1表示小于
     */
    public static int compareTime(Date timeOne,Date timeTwo) {
        long t1=DateUtils.getFragmentInSeconds(timeOne, Calendar.DATE);
        long t2=DateUtils.getFragmentInSeconds(timeTwo, Calendar.DATE);
        return t1 > t2 ? 1 : t1 < t2 ? -1 : 0;
    }
    
    /**
     * 
      * getWeek(获取日期是星期几)
      *
      * @Title: getWeek
      * @param date 时间
      * @return int    返回类型
      * @throws
     */
    public static int getWeek(Date date){
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return week;
    }
    
    /**
     * 
      * getStartTimeOfYear(获取某年的第一时刻)
      *
      * @Title: getStartTimeOfYear
      * @param date dateYear
      * @return Date    返回类型
      * @throws
     */
    public static Date getStartTimeOfYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, DAY_OF_1);
        c.set(Calendar.HOUR_OF_DAY, HOUR_OF_00);
        c.set(Calendar.MINUTE, MINUTE_OF_0);
        c.set(Calendar.SECOND, SECOND_OF_0);
        c.set(Calendar.MILLISECOND, MILLISECOND_OF_0);
        return c.getTime();
    }
    
    /**
     * 
      * getLastTimeOfYear(获取某年的最后一个时刻)
      *
      * @Title: getLastTimeOfYear
      * @param date dateyear
      * @return    设定文件
     */
    public static Date getLastTimeOfYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, Calendar.DECEMBER);
        c.set(Calendar.DAY_OF_MONTH, DAY_OF_31);
        c.set(Calendar.HOUR_OF_DAY, HOUR_OF_23);
        c.set(Calendar.MINUTE, MINUTE_OF_59);
        c.set(Calendar.SECOND, SECOND_OF_59);
        c.set(Calendar.MILLISECOND, MILLISECOND_OF_999);
        return c.getTime();
    }

    /**
      * @Title: is30DayBefore
      * @Description: 判断传入日期是否比系统时间大30天
      * @param date 传入日期
      * @return boolean    返回类型
      */
    public static boolean is30DayBefore(Date date) {
        long sub = System.currentTimeMillis() - date.getTime();
        return sub >= LAST_MONTH_DAYS * MILLISECS_PER_DAY;
    }
    
    /**
      * getDayOfMonth(获得日期是一个月的第几天)
      *
      * @Title: getDayOfMonth
      * @param date 日期
      * @return
      * @return int    返回类型
      * @throws
      */
    public static int getDayOfMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
      * getMaxDayOfMonth(返回一个月总共有几天)
      *
      * @Title: getMaxDayOfMonth
      * @param date 日期
      * @return
      * @return int    返回类型
      * @throws
      */
    public static int getMaxDayOfMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 比较两个日期的相差天数
     * @param fDate
     * @param oDate
     * @return  
     * 			:oDate - fDate
     */
    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
     }
    
    public static Date getAssignDate(Date date,String time){
        String dat = dateToString(date,"yyyy-MM-dd");
        String dataDate = dat+" "+time;
        return stringToDate(dataDate, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * main(这里用一句话描述这个方法的作用)
     * 
     * @Title: main
     * @param args
     *            参数
     */
    public static void main(String[] args) {
//        Date date = DateUtil.stringToDate("2013-03-12", "yyyy-MM-dd");
//        Date date1 = DateUtil.dateToDateTime(date);
//        Date date2 = DateUtil.dateToDateTime(new Date());
        
        Date date3 = DateUtil.stringToDate("2013-03-12 12:02:02", "yyyy-MM-dd HH:mm:ss");
        Date date4 = DateUtil.stringToDate("2055-03-13 12:02:00", "yyyy-MM-dd HH:mm:ss");
        int c=DateUtil.compareTime(date3,date4);
        assert c==1;   //-enableassertions
//        String dateStr = DateUtil.dateToString(new Date(), "HH:mm:ss");
//        int m = DateUtil.getMonth(new Date());
//        int d = DateUtil.getDay(new Date());
//        int h = DateUtil.getHour(new Date());
//        int mm = DateUtil.getMinute(new Date());
        Date date = getAssignDate(new Date(), "09:30:00");
        System.out.println(DateUtil.dateToString(date, "yyyy-MM-dd HH:mm:ss"));
    }
}
