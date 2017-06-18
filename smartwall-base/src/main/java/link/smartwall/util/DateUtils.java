/**
 * CellSoft 2010
 */
package link.smartwall.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期处理实用类
 * 
 * @author lexloo
 * @version 1.0 2010-8-18
 * @since CS 1.0
 */
public final class DateUtils {
    /**
     * 构造函数
     */
    private DateUtils() {
        // Utility class
    }

    /**
     * 日志处理类
     */
    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class.getName());

    /**
     * 日期时间24小时制格式("yyyy-MM-dd HH:mm:ss")
     */
    public static final String DATE_TIME_24_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期时间24小时制格式("yyyy-MM-dd HH:mm")
     */
    public static final String DATE_TIME_24_FORMAT2 = "yyyy-MM-dd HH:mm";
    /**
     * 时间24小时制格式("HH:mm")
     */
    public static final String TIME_24_FORMAT = "HH:mm";

    /**
     * 日期格式("yyyy-MM-dd")
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 日期格式("yyyy/MM/dd")
     */
    public static final String DATE_FORMAT_SLASH = "yyyy/MM/dd";

    /**
     * 短日期格式
     */
    public static final String DATE_FORMAT_SHORT = "yyyyMMdd";
    /**
     * 日期时间24小时制格式("yyyyMMddHHmmss")
     */
    public static final String DATE_TIME_24_FORMAT_SHORT = "yyyyMMddHHmmss";
    /**
     * 日期间隔计算因子
     */
    private static final float DAYS_SPAN_FACTOR = 1000 * 3600 * 24f;
    /**
     * 时间间隔计算因子
     */
    private static final float HOURS_SPAN_FACTOR = 1000 * 3600f;

    /**
     * 时间间隔计算因子
     */
    private static final float MINUTES_SPAN_FACTOR = 1000 * 60f;

    /**
     * 日期字符串转化为日期
     * 
     * @param dateTime yyyy-MM-dd HH:mm:ss 格式时间串
     * @return 时间对象
     */
    public static Date converToDateTime(String dateTime) {
        if (null == dateTime || "".equals(dateTime)) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_24_FORMAT);
        try {
            return df.parse(dateTime);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }
            return new Date();
        }
    }

    /**
     * 日期字符串转化为日期
     * 
     * @param dateTime yyyy-MM-dd HH:mm:ss 格式时间串
     * @param format format
     * @return 时间对象
     */
    public static Date converToDate(String dateTime, String format) {
        if (null == dateTime || "".equals(dateTime)) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(dateTime);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }
            return new Date();
        }
    }

    /**
     * 转换为日期
     * 
     * @param date 短格式日期
     * @return 日期对象
     */
    public static Date convertToDateShort(String date) {
        if (null == date || "".equals(date)) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_SHORT);
        try {
            return df.parse(date);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }

            return new Date();
        }
    }

    /**
     * 转换为日期
     * 
     * @param date 短格式日期
     * @return 日期对象
     */
    public static Date convertToDateShort2(String date) {
        if (null == date || "".equals(date)) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        // 设置当日期非法时，直接返回null
        df.setLenient(false);
        try {
            return df.parse(date);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }

            return null;
        }
    }

    /**
     * 转换为日期
     * 
     * @param date 短格式日期
     * @return 日期对象
     */
    public static Date convertToDateShort3(String date) {
        if (null == date || "".equals(date)) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_24_FORMAT);
        // 设置当日期非法时，直接返回null
        df.setLenient(false);
        try {
            return df.parse(date);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }

            return null;
        }
    }

    /**
     * 日期字符串转化为日期
     * 
     * @param dateTime yyyyMMddHHmmss 格式时间串
     * @return 时间对象
     */
    public static Date converToDateTimeShort(Object dateTime) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_24_FORMAT_SHORT);
        try {
            return df.parse(StrUtils.c2str(dateTime));
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }

            return new Date();
        }
    }

    /**
     * 日期字符串转化为日期
     * 
     * @param dateTime yyyy-MM-dd HH:mm:ss 格式时间串
     * @param format 格式
     * @return 时间对象
     */
    public static Date converToDateTime(String dateTime, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);

        try {
            return df.parse(dateTime);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }

            return new Date();
        }
    }

    /**
     * 使用自定义格式格式化当前日期
     * 
     * @param dateFormat 输出显示的时间格式
     * @return 格式化结果
     */
    public static String formatCurrentDate(String dateFormat) {
        return formatDate(new Date(), dateFormat);
    }

    /**
     * 使用默认格式(yyyy-MM-dd)格式化当前日期
     * 
     * @return 格式化结果
     */
    public static String formatCurrentDate() {
        return formatCurrentDate(DATE_FORMAT);
    }

    /**
     * 格式化本月第一天
     * 
     * @return 本月第一天格式化数据
     */
    public static String formatFirstDayOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return formatDate(cal.getTime());
    }

    /**
     * 使用默认格式(HH:mm)格式化当前日期
     * 
     * @return 格式化结果
     */
    public static String formatCurrentTime() {
        return formatCurrentDate(TIME_24_FORMAT);
    }

    /**
     * 格式化日期，自定义格式
     * 
     * @param date 日期
     * @param dateFormat 日期格式
     * @return 格式化结果
     */
    public static String formatDate(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        return sdf.format(date);
    }

    /**
     * 使用默认格式(yyyy-MM-dd)格式化日期
     * 
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "-";
        }

        return formatDate(date, DATE_FORMAT);
    }

    /**
     * 使用自定义格式格式化当前时间
     * 
     * @param dateTime 日期时间
     * @param datetimeFormat 日期时间格式
     * @return 格式化结果
     */
    public static String formatDateTime(Date dateTime, String datetimeFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(datetimeFormat);

        return sdf.format(dateTime);
    }

    /**
     * 使用默认格式(yyyy-MM-dd HH:mm:ss)格式化日期时间,24小时制
     * 
     * @param dateTime 日期时间
     * @return 格式化结果
     */
    public static String formatDateTime(Date dateTime) {
        return formatDateTime(dateTime, DATE_TIME_24_FORMAT);
    }

    /**
     * 使用格式(yyyy-MM-dd HH:mm:ss)格式化当前日期
     * 
     * @return 格式化结果
     */
    public static String formatCurrentDateTime() {
        return formatDateTime(new Date(), DATE_TIME_24_FORMAT);
    }

    /**
     * 获取日历0点
     * 
     * @param date 日期
     * @return 日历0点
     */
    public static Calendar getZeroCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    /**
     * 获取日历昨天0点
     * 
     * @param date 今天日期
     * @return 日历昨天0点
     */
    public static Calendar getPreZeroCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    /**
     * 获取日历今天23点30分
     * 
     * @param date 今天日期
     * @return 日历今天23点30分
     */
    public static Calendar getLateCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    /**
     * 20090904170000.000字符串转化为时间
     * 
     * @param timeStr 时间串
     * @return 时间字符串
     */
    public static String toTimeStampString(String timeStr) {
        String result = "";
        // TimeZone tz = TimeZone.getDefault();
        // GregorianCalendar c = new GregorianCalendar(tz);
        result += timeStr.substring(0, 4);
        result += "-";
        result += timeStr.substring(4, 6);
        result += "-";
        result += timeStr.substring(6, 8);

        if (timeStr.length() > 8) {
            result += " " + timeStr.substring(8, 10);
            result += ":";
            result += timeStr.substring(10, 12);
            result += ":";
            result += timeStr.substring(12, 14);
        }
        return result;
    }

    /**
     * *********************************************************** 根据当前时间获取下周一日期
     * 
     * @param sDate 比较时间
     * @param count 下几周
     * @return Date
     * @author dujj
     */
    public static Date getNextMonday(Date sDate, int count) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(sDate);
        int week = cal.get(Calendar.DAY_OF_WEEK);

        if (week > 1) {
            cal.add(Calendar.DAY_OF_MONTH, -(week - 1) + 7 * count);
        }

        // else {
        // cal.add(Calendar.DAY_OF_MONTH, 1 - week + 7 * count);
        // }

        cal.add(Calendar.DATE, 1);

        return formatDate2(cal.getTime(), DATE_FORMAT_SLASH);
    }

    /**
     * *********************************************************** 根据当前时间获取上周日日期
     * 
     * @param sDate 比较时间
     * @param count 上几周
     * @return Date
     * @author dujj
     */
    public static Date getLastSunday(Date sDate, int count) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(sDate);
        int week = cal.get(Calendar.DAY_OF_WEEK);

        if (week > 1) {
            cal.add(Calendar.DAY_OF_MONTH, -(week - 7) - 7 * count);
        }

        // else {
        // cal.add(Calendar.DAY_OF_MONTH, 1 - week + 7 * count);
        // }

        cal.add(Calendar.DATE, 1);

        return formatDate2(cal.getTime(), DATE_FORMAT_SLASH);
    }

    /**
     * 根据当前时间获取下月一号日期
     * 
     * @param sDate 比较时间
     * @param count 下几月
     * @return Date
     * @author dujj
     */
    public static Date getNextMonth(Date sDate, int count) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(sDate);
        cal.add(Calendar.MONTH, count);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return formatDate2(cal.getTime(), DATE_FORMAT_SLASH);
    }

    /**
     * 获取当前月最后一天
     * 
     * @return last day
     */
    public static int getCurrMonthLastDay() {

        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("d");

        return NumberUtils.c2int(sdf.format(cal.getTime()));
    }

    /**
     * @param year int 年份
     * @param month int 月份
     * @return int 某年某月的最后一天
     */
    public static int getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        // 某年某月的最后一天
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 根据当前时间获取下一天日期
     * 
     * @param sDate 比较时间
     * @param count 下几天
     * @return Date
     * @author dujj
     */
    public static Date getNextDay(Date sDate, int count) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(sDate);
        cal.add(Calendar.DAY_OF_YEAR, count);

        return formatDate2(cal.getTime(), DATE_FORMAT_SLASH);
    }

    /**
     * 时间格式化
     * 
     * @param sdate sdate
     * @param formats formats
     * @return Date
     */
    public static Date formatDate2(Date sdate, String formats) {

        SimpleDateFormat df = new SimpleDateFormat(formats);
        String date = df.format(sdate);

        try {
            return df.parse(date);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }

            return null;
        }
    }

    /**
     * 日期之间加减
     * 
     * @param cdate 当前日期
     * @param calendarType Calendar.DAY_OF_YEAR/Calendar.MONTH
     * @param num 加减数量
     * @return date
     */
    public static Date addDate(Date cdate, int calendarType, int num) {

        Calendar ec = Calendar.getInstance();
        ec.setTime(cdate);
        ec.add(calendarType, num);
        return ec.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     * 
     * @param smdate <String>
     * @param bdate <String>
     * @return int
     */
    public static int monthsBetween(Date smdate, Date bdate) {
        int result = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(smdate);
        c2.setTime(bdate);
        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return result == 0 ? 1 : Math.abs(result);
    }

    /**
     * 计算两个日期之间相差的月数
     * 
     * @param smdate <String>
     * @param bdate <String>
     * @return int
     */
    public static int monthsBetween2(Date smdate, Date bdate) {
        int result = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(smdate);
        c2.setTime(bdate);
        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return Math.abs(result);
    }

    /**
     * 计算两个日期之间相差的天数
     * 
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     */
    public static int daysBetween(Date smdate, Date bdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        int betweenDays = (int) ((time2 - time1) / DAYS_SPAN_FACTOR);

        return Math.abs(betweenDays);
    }

    /**
     * 计算两个日期之间相差的天数
     * 
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     */
    public static double hourBetween(Date smdate, Date bdate) {
        double t = smdate.getTime() - bdate.getTime();

        double betweenHours = t / HOURS_SPAN_FACTOR;

        return Math.abs(betweenHours);
    }

    /**
     * 计算两个日期之间相差的分钟数(取正)
     * 
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     */
    public static double minutesBetween(Date smdate, Date bdate) {
        double t = smdate.getTime() - bdate.getTime();

        double betweenMinutes = t / MINUTES_SPAN_FACTOR;

        return Math.abs(betweenMinutes);
    }

    /**
     * 计算两个日期之间相差的分钟数
     * 
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     */
    public static double minutes(Date smdate, Date bdate) {
        return (bdate.getTime() - smdate.getTime()) / MINUTES_SPAN_FACTOR;
    }

    /**
     * 是否是有效的日期格式串
     * 
     * @param dateStr 日期串
     * @return true or false
     */
    public static boolean isValidDateStr(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            df.parse(dateStr);

            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    /**
     * 传一个数字过来，小于十的话 前面加个0
     * 
     * @param value 输入字符串
     * @return 结果
     */
    public static String formatDay(int value) {
        return (String) (value < 10 ? "0" + value : value);
    }
    
    /**
     * 获取本月最后一天日期
     * @return
     */
    public static String getMonthLastDay() {     
        Calendar calendar = Calendar.getInstance();     
        calendar.set(Calendar.DAY_OF_MONTH, calendar     
                .getActualMaximum(Calendar.DAY_OF_MONTH));     
        return formatDate(calendar.getTime());    
    }    
    /**
     * 获取本月第一天日期
     * @return
     */
    public static String getMonthFirstDay() {     
        Calendar calendar = Calendar.getInstance();     
        calendar.set(Calendar.DAY_OF_MONTH, calendar     
                .getActualMinimum(Calendar.DAY_OF_MONTH));     
        return formatDate(calendar.getTime());     
    }  
    /**
     * 获取下月某天日期
     * @return
     * @throws ParseException 
     */
    public static String getNextMonthDay(Date date) {     
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);  
    	calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
        return formatDate(calendar.getTime());     
    }  
    /** 
     * 得到几天后的时间 
     *  
     * @param d 
     * @param day 
     * @return 
     */  
    public static Date getDateAfter(Date d, int day) {  
        Calendar now = Calendar.getInstance();  
        now.setTime(d);  
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);  
        return now.getTime();  
    }  
    /** 
     * 得到某日期天数
     *  
     * @param d 
     * @param day 
     * @return 
     */  
    public static Integer getDay(Date date) {  
        Calendar now = Calendar.getInstance();  
        now.setTime(date);  
        return now.get(Calendar.DAY_OF_MONTH);  
    }  
}
