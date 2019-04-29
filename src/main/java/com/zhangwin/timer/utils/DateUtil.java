package com.zhangwin.timer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yangchangjian
 * @version 1.0
 * @Description:
 * @datatime 2019/3/14 19:39
 */
public class DateUtil {
    private static String yyyy_mm_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static String yyyymmdd = "yyyyMMdd";
    public static String yyyy_mm_dd = "yyyy-MM-dd";

    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(yyyy_mm_dd_hh_mm_ss);
        return format.format(date);
    }

    public static String formatDate(Date date, String match) {
        SimpleDateFormat format = new SimpleDateFormat(match);
        return format.format(date);
    }
}
