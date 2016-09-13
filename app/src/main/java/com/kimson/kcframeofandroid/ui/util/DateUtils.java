package com.kimson.kcframeofandroid.ui.util;

import android.content.Context;

import com.kimson.kcframeofandroid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhujianheng on 8/6/16.
 */
public class DateUtils extends com.kimson.library.util.DateUtils {



    /**
     * 转换为友好的时间显示
     *
     * @param context
     * @param dateStr
     * @return
     */
    public static String toTimeAgo(Context context, String dateStr) {
        final String dateFormatPattern = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern);
        // 转换字符串为Date
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            return dateStr;
        }
        Calendar ago = Calendar.getInstance();
        ago.setTime(date);
        long duration = Math.abs(System.currentTimeMillis() - ago.getTimeInMillis());
        if (duration < MINUTE_IN_MILLIS) {
            return context.getString(R.string.public_just_now);
        }
        return String.valueOf(getRelativeTimeSpanString(ago.getTimeInMillis()));
    }

    public static String format(String pattern, long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        Date date = c.getTime();
        return format(pattern, date);
    }

    public static String format(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parse(String pattern, String datetime) {
        try {
            return new SimpleDateFormat(pattern).parse(datetime);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String format(String pattern, String dateStr) {
        Date date = DateUtils.parse("yyyy-MM-dd'T'HH:mm:ss", dateStr);
        return format(pattern, date);
    }

    public static String formatToDate(String pattern, String dateStr) {
        Date date = DateUtils.parse("yyyy-MM-dd", dateStr);
        return format(pattern, date);
    }

    public static String toYyyyMMdd(String dateStr) {
        try {
            Date date = DateUtils.parse("yyyy-MM-dd'T'HH:mm:ss", dateStr);
            return DateUtils.format("yyyy-MM-dd", date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String toDateandTime(String dateStr) {
        try {
            Date date = DateUtils.parse("yyyy-MM-dd'T'HH:mm:ss", dateStr);
            return DateUtils.format("yyyy-MM-dd hh:mm", date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 返回时间，并且可以进行小时的相加减
     * @param time
     * @return
     */
    public static String addHourStr(String time, int hour) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return hourFormat.format(calendar.getTime());
    }
}
