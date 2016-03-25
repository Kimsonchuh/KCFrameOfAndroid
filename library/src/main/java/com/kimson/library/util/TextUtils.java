package com.kimson.library.util;

import java.util.regex.Pattern;

/**
 * Created by zhujianheng on 2/23/16.
 */
public class TextUtils {
    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    /**
     * 判断字符串是否为手机号
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        if (str.replace("-", "").length() == 11) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return android.text.TextUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否为Email
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        if (str == null || str.trim().length() == 0)
            return false;
        return emailer.matcher(str).matches();
    }

}
