package com.shenyuan.superapp.base.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author ch
 * 时间： 2018/11/26 0026-上午 11:11
 * 描述： 日期格式工具类
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {

    private static final SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat YYYYMMDDHHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat YYYYMM = new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat HHMMSS = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat HHMM = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat MMSS = new SimpleDateFormat("mm:ss");
    private static final SimpleDateFormat MMMM = new SimpleDateFormat("MM-dd HH:mm");


    /**
     * yyyy-MM-dd HH:mm
     *
     * @param time 毫秒值
     * @return yyyy-MM-dd HH:mm
     */
    public static synchronized String getFormatDateYm(long time) {
        return YYYYMMDDHHMM.format(new Date(time));
    }

    /**
     * mm:ss
     *
     * @param time 毫秒值
     * @return mm:ss
     */
    public static synchronized String getFormatDateMs(long time) {
        return MMSS.format(new Date(time));
    }

    /**
     * MM-dd HH:mm
     *
     * @param time 毫秒值
     * @return mm:ss
     */
    public static synchronized String getFormatDateMM(long time) {
        return MMMM.format(new Date(time));
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param time 毫秒值
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static synchronized String getFormatDateYs(long time) {
        return YYYYMMDDHHMMSS.format(new Date(time));
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param time 毫秒值
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static synchronized long getFormatDateYs(String time) {
        try {
            return YYYYMMDDHHMMSS.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * HH:mm:ss
     *
     * @param time 毫秒值
     * @return HH:mm:ss
     */
    public static synchronized String getFormatDateHs(long time) {
        HHMMSS.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return HHMMSS.format(time);
    }

    /**
     * yyyy-MM-dd
     *
     * @param time 毫秒值
     * @return yyyy-MM-dd
     */
    public static synchronized String getFormatDateYd(long time) {
        return YYYYMMDD.format(new Date(time));
    }

    /**
     * HH:mm
     *
     * @param time 毫秒值
     * @return yyyy-MM-dd
     */
    public static synchronized String getFormatDateHM(long time) {
        return HHMM.format(new Date(time));
    }


    /**
     * 获取当前月份
     *
     * @return 月份
     */
    public static int getCurrentMonth() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MONTH);
    }

    /**
     * 获取当前年份
     *
     * @return 年份
     */
    public static int getCurrentYear() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    /**
     * 获取指定月的第一天
     *
     * @param time 2020-04
     * @return 2020-04-01 00:00:00
     */
    public static String getMonthStartDay(String time) {
        String firstday = "";
        try {
            Date date = YYYYMM.parse(time);
            Calendar cale = Calendar.getInstance();
            if (date != null) {
                cale.setTime(date);
            }
            int last = cale.getActualMinimum(Calendar.DAY_OF_MONTH);
            cale.set(Calendar.DAY_OF_MONTH, last);
            firstday = YYYYMMDDHHMMSS.format(cale.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return firstday;
    }

    /**
     * 获取指定月的最后一天
     *
     * @param time 2020-04
     * @return 2020-04-30 23:59:59
     */
    public static String getMonthEndDay(String time) {
        String firstday = "";
        try {
            Date date = YYYYMM.parse(time);
            Calendar cale = Calendar.getInstance();
            if (date != null) {
                cale.setTime(date);
            }
            int last = cale.getActualMaximum(Calendar.DAY_OF_MONTH);
            cale.set(Calendar.DAY_OF_MONTH, last);
            firstday = YYYYMMDD.format(cale.getTime()) + " 23:59:59";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return firstday;
    }

    /**
     * 获取周几
     *
     * @param time 毫秒值
     * @return 星期
     */
    public static String getWeek(long time) {
        String week = "";
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(time));
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) {
            week = "星期日";
        } else if (weekday == 2) {
            week = "星期一";
        } else if (weekday == 3) {
            week = "星期二";
        } else if (weekday == 4) {
            week = "星期三";
        } else if (weekday == 5) {
            week = "星期四";
        } else if (weekday == 6) {
            week = "星期五";
        } else if (weekday == 7) {
            week = "星期六";
        }
        return week;
    }

    /**
     * 获取2个日期中间的天数
     *
     * @param start start
     * @param end   end
     * @return 天数
     */
    public static int getBetweenDays(String start, String end) {
        try {
            long s = YYYYMMDD.parse(start).getTime();
            long e = YYYYMMDD.parse(end).getTime();
            int betweenDays = (int) (Math.abs(e - s) / (24 * 3600 * 1000));
            return betweenDays + 1;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
