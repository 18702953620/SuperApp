package com.shenyuan.superapp.base.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @author ch
 * @date 2021/2/20 12:06
 * desc
 */
public class StrUtils {
    /**
     * 去除null
     *
     * @param str str
     * @return String
     */
    public static String isEmpty(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str;
        } else {
            return "";
        }
    }

    /**
     * 去除null
     *
     * @param obj str
     * @return String
     */
    public static String isEmpty(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    /**
     * 去除double 计数问题
     *
     * @param dl dl
     * @return String
     */
    public static String valueOf(double dl) {
        return new BigDecimal(dl).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 必须是字母、数字、特殊字符任意2中或者2种以上的组合，长度为8--16
     *
     * @param pwd pwd
     * @return boolean
     */
    public static boolean checkPwd(String pwd) {
        String reg = "^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{8,16}$";
        return Pattern.matches(reg, pwd);
    }
}
