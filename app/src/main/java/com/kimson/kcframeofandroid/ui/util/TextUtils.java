package com.kimson.kcframeofandroid.ui.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhujianheng on 8/17/16.
 */
public class TextUtils extends com.kimson.library.util.TextUtils {


    /**
     * md5加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    /**
     * 以指定字符分割字符串
     * @param string
     * @param target
     * @return
     */
    public static String[] divideStringByChar(String string, String target) {
        return string.split(target);
    }

}
