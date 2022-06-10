package com.shenyuan.superapp.base.utils;

import android.text.TextUtils;

public class ParseUtils {
    /**
     * String-->int
     *
     * @param s String
     * @return int
     */
    public static int parseInt(String s) {
        if (TextUtils.isEmpty(s)) {
            return 0;
        }
        int t;
        try {
            t = Integer.parseInt(s);
        } catch (Exception e) {
            t = 0;
        }
        return t;
    }

    /**
     * String-->int
     *
     * @param s String
     * @return long
     */
    public static long parseLong(String s) {
        if (TextUtils.isEmpty(s)) {
            return 0;
        }
        long t;
        try {

            t = Long.parseLong(s);
        } catch (Exception e) {
            t = 0;
        }
        return t;
    }

    /**
     * String-->double
     *
     * @param s String
     * @return double
     */
    public static double parseDouble(String s) {
        if (TextUtils.isEmpty(s)) {
            return 0;
        }
        double t;
        try {

            t = Double.parseDouble(s);
        } catch (Exception e) {
            t = 0;
        }
        return t;
    }

    /**
     * String-->double
     *
     * @param s String
     * @return float
     */
    public static float parseFloat(String s) {
        if (TextUtils.isEmpty(s)) {
            return 0;
        }

        float t;
        try {
            t = Float.parseFloat(s);
        } catch (Exception e) {
            t = 0;
        }
        return t;
    }


    /**
     * 手机号码中间四位修改为*
     *
     * @param phone phone
     * @return String
     */
    public static String getPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return "";
        }
        return phone.replaceAll("(?<=[\\d]{3})\\d(?=[\\d]{4})", "*");
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNo(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         * "[1]"代表第1位为数字1，"[35789]"代表第二位可以为3、5、7、8、9中的一个，"\\d{9}"代表后面是可以是0～9的数字，
         * 有9位。
         */
        String telRegex = "[1][35789]\\d{9}";
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }
}

