package ru.ktoddler.util;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    final static Calendar cal = getCalendarUTC();

    public static final long MILLIS_PER_SECOND = 1000;
    public static final long MILLIS_PER_MINUTE = 60000;
    public static final long SECONDS_PER_HOUR = 3600;

    private DateUtils() {
    }

    public static long getCurrentSeconds() {
        return getCurrentMillis() / MILLIS_PER_SECOND;
    }

    public static long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    public static long getCurrentNanos() {
        return System.nanoTime();
    }

    public static String format(long mills, String formatPattern) {
        // example "yyyy-MM-dd hh:mm:ss"
        Date dateTime = new Date(mills);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);

        return sdf.format(dateTime);
    }

    private static CharSequence dateFormat(String formatPattern, Calendar cal) {
        return DateFormat.format(formatPattern.replace("Y","y").replace("D", "d"), cal);
    }

    public static String formatUTC(long dateMillis, String formatPattern) {
        cal.setTimeInMillis(dateMillis);
        return dateFormat(formatPattern, cal).toString();
    }

    public static Calendar getCalendarUTC() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    public static long startOfDay(long millis) {
        cal.setTimeInMillis(millis);
        cal.set(Calendar.HOUR_OF_DAY, 0); //set hour to last hour
        cal.set(Calendar.MINUTE, 0); //set minutes to last minute
        cal.set(Calendar.SECOND, 0); //set seconds to last second
        cal.set(Calendar.MILLISECOND, 0); //set milliseconds to last millisecond
        return cal.getTimeInMillis();
    }

    public static long endOfDay(long millis) {
        cal.setTimeInMillis(millis);
        cal.set(Calendar.HOUR_OF_DAY, 23); //set hour to last hour
        cal.set(Calendar.MINUTE, 59); //set minutes to last minute
        cal.set(Calendar.SECOND, 59); //set seconds to last second
        cal.set(Calendar.MILLISECOND, 999); //set milliseconds to last millisecond
        return cal.getTimeInMillis();
    }

    public static Calendar getCalendarUTC(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = getCalendarUTC();
        calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
