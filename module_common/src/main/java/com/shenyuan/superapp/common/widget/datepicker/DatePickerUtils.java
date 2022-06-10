package com.shenyuan.superapp.common.widget.datepicker;

import android.content.Context;
import android.widget.TextView;

import com.shenyuan.superapp.base.utils.DateUtils;

/**
 * @author ch
 * @date 2021/3/15 14:36
 * desc
 */
public class DatePickerUtils {

    /**
     * 年月日选择
     *
     * @param context context
     * @param tvYear  tvYear
     */
    public static void showYYMMDDDialog(Context context, TextView tvYear) {
        String startTime = "2000-01-01 00:00";
        String endTime = "2050-12-31 00:00";
        // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        CustomDatePicker customDatePicker = new CustomDatePicker(context, time -> {
            String year = time.split(" ")[0];
            tvYear.setText(year);
        }, startTime, endTime);

        // 显示年、月、日
        customDatePicker.showYearMonthDay();
        // 不允许循环滚动
        customDatePicker.setIsLoop(false);
        customDatePicker.show(DateUtils.getFormatDateYm(System.currentTimeMillis()));
    }

    /**
     * 年月日选择
     *
     * @param context context
     */
    public static void showYYMMDDDialog(Context context, CustomDatePicker.ResultHandler handler) {
        String startTime = "2000-01-01 00:00";
        String endTime = "2050-12-31 00:00";
        // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        CustomDatePicker customDatePicker = new CustomDatePicker(context, handler, startTime, endTime);

        // 显示年、月、日
        customDatePicker.showYearMonthDay();
        // 不允许循环滚动
        customDatePicker.setIsLoop(false);
        customDatePicker.show(DateUtils.getFormatDateYm(System.currentTimeMillis()));
    }


    /**
     * 年月日选择
     *
     * @param context context
     * @param tvYear  tvYear
     */
    public static void showYYMMDDDialog(Context context, TextView tvYear, String start) {
        String startTime = start + " 00:00";
        String endTime = "2050-12-31 00:00";
        // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        CustomDatePicker customDatePicker = new CustomDatePicker(context, time -> {
            String day = time.split(" ")[0];
            tvYear.setText(day);
        }, startTime, endTime);

        // 显示年、月、日
        customDatePicker.showYearMonthDay();
        // 不允许循环滚动
        customDatePicker.setIsLoop(false);
        customDatePicker.show(startTime);
    }

    /**
     * 年月日选择
     *
     * @param context context
     */
    public static void showYYMMDDDialog(Context context, CustomDatePicker.ResultHandler handler, String start) {
        String startTime = start + " 00:00";
        String endTime = "2050-12-31 00:00";
        // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        CustomDatePicker customDatePicker = new CustomDatePicker(context, handler, startTime, endTime);

        // 显示年、月、日
        customDatePicker.showYearMonthDay();
        // 不允许循环滚动
        customDatePicker.setIsLoop(false);
        customDatePicker.show(startTime);
    }
}
