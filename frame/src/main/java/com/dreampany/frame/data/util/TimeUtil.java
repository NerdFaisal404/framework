package com.dreampany.frame.data.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nuc on 10/3/2015.
 */
public final class TimeUtil {

    private TimeUtil() {
    }

    public static final long DRAWER_LAUNCH_DELAY = 250L;
    public static final long LOAD_DELAY = 1000L;

    public static final long second = 1000;
    public static final long minute = 60 * second;
    public static final long hour = 60 * minute;
    public static final long day = 24 * hour;
    public static final long week = 7 * day;

    private static final String timeZone = "UTC";

    public static long secondToMilli(long seconds) {
        return seconds * second;
    }

    public static long minuteToMilli(long minutes) {
        return minutes * minute;
    }

    public static long getHalfDayFromCurrent() {
        return currentTime() + (day / 2);
    }

    public static long getStartOfDay() {
        return getStartOfDayInMillis(currentTime());
    }

    public static long getStartOfDayInMillis(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getEndOfDayInMillis(long millis) {
        return getStartOfDayInMillis(millis) + day;
    }

    public static long startOfToday() {
        DateTimeZone timeZone = DateTimeZone.forID("America/Montreal");
        DateTime now = DateTime.now(timeZone);
        DateTime todayStart = now.withTimeAtStartOfDay();
        DateTime tomorrowStart = now.plusDays(1).withTimeAtStartOfDay();
        Interval today = new Interval(todayStart, tomorrowStart);
        return 0;
    }

    public static long addDay(long time) {
        return TimeUtil.getStartOfDayInMillis(resolveDay(time, 1));
    }

    public static long removeDay(long time) {
        return TimeUtil.getStartOfDayInMillis(resolveDay(time, -1));
    }

    public static long resolveDay(long time, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        calendar.add(Calendar.DATE, days);
        return calendar.getTime().getTime();
    }

    public static long getPreviousDateTime(long time) {
        DateTime dt = new DateTime(time, DateTimeZone.UTC);
        return TimeUtil.getStartOfDayInMillis(dt.minusDays(1).getMillis());
    }

    /**
     * Basic regex for parsing ISO-8601 durations.
     */
    private static final Pattern LENGTH_PATTERN = Pattern.compile("^PT(?:([0-9]+)H)?(?:([0-9]+)M)?(?:([0-9]+)S)?$", Pattern.CASE_INSENSITIVE);
    //    private static final String patternFull = "MMM dd, yyyy, hh:mm aaa";
    private static final String patternFull = "yy_MM_dd_hh:mm";
    private static final String pattern = "MMM dd, yyyy";
    private static final String patternToday = "hh:mm aaa";
    private static final String patternTime = "hh:mm:ss";
    private static final String patternDay = "EEEE";
    private static final String patternDate = "MM/dd/yy";
    private static final String patternDateWordnik = "yyyy-MM-dd"; // wordnik support date pattern

    private static final SimpleDateFormat dateFormatFull = new SimpleDateFormat(patternFull, Locale.getDefault());
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
    private static final SimpleDateFormat dateFormatToday = new SimpleDateFormat(patternToday, Locale.getDefault());
    private static final SimpleDateFormat dateFormatTime = new SimpleDateFormat(patternTime, Locale.getDefault());
    private static final SimpleDateFormat dateFormatWeek = new SimpleDateFormat(patternDay, Locale.getDefault());
    private static final SimpleDateFormat dateFormatDate = new SimpleDateFormat(patternDate, Locale.getDefault());
    private static final SimpleDateFormat dateFormatDateWordnik = new SimpleDateFormat(patternDateWordnik, Locale.getDefault());

    public static String toLocalTime(long time) {
        return dateFormat.format(time);
    }

    public static long currentTime() {
        return System.currentTimeMillis();
    }

    public static long getWeekDelayTime() {
        long currentTime = currentTime();

        return currentTime - week;
    }

    public static boolean isDelayed(long time, long delay) {
        return currentTime() - time >= delay;
    }

    public static long getDelayTime(long time) {
        return currentTime() - time;
    }

    public static boolean isIn24(long time) {
        long currentTime = currentTime();

        return (currentTime - time) <= day;
    }

    public static String getTimeInFullPattern(long time) {
        return dateFormatFull.format(time);
    }

    public static long toMilli(String date) {
        try {
            return dateFormatDateWordnik.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static String getCurrentDate() {
        return dateFormat.format(currentTime());
    }

    public static String getDateFromTime(long time) {
        return dateFormat.format(time);
    }

    public static Date getDateFromTimeInDateFormat(long time) {
        return new Date(time);
    }

    public static String getTodayTimeFromTime(long time) {
        return dateFormatToday.format(time);
    }

    public static String getDayFromTime(long date) {
        return dateFormatWeek.format(date);
    }

    public static String getShortDateFromTime(long date) {
        if (date <= 0L) {
            return null;
        }
        return dateFormatDate.format(date);
    }

    public static Map<TimeUnit, Long> getDateAsMapWithTimeUnit(long newestDate, long oldestDate) {

        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);

        Map<TimeUnit, Long> result = new LinkedHashMap<>();
        long milliRest = newestDate - oldestDate;

        if (oldestDate > newestDate) {
            milliRest = oldestDate - newestDate;
        }

        for (TimeUnit unit : units) {

            long diff = unit.convert(milliRest, TimeUnit.MILLISECONDS);
            long diffInMilliForUnit = unit.toMillis(diff);
            milliRest = milliRest - diffInMilliForUnit;
            result.put(unit, diff);
        }

        return result;
    }

    public static Map<TimeUnit, Long> getDateAsMapWithTimeUnit(long milli) {

        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);

        Map<TimeUnit, Long> result = new LinkedHashMap<TimeUnit, Long>();
        long milliRest = milli;

        for (TimeUnit unit : units) {

            long diff = unit.convert(milliRest, TimeUnit.MILLISECONDS);
            long diffInMilliForUnit = unit.toMillis(diff);
            milliRest = milliRest - diffInMilliForUnit;
            result.put(unit, diff);
        }

        return result;
    }

    public static boolean getIsToday(Map<TimeUnit, Long> dateMap) {
        boolean isToday = false;

        if (dateMap != null) {
            isToday = dateMap.get(TimeUnit.DAYS) == 0;
        }

        return isToday;
    }

    public static boolean getIsToday(long time) {
        return Days.daysBetween(new LocalDate(time), new LocalDate(currentTime())).getDays() == 0;
    }

    public static String getDate() {
        return getDate(currentTime());
    }

    public static String getDate(long time) {
        return dateFormatDateWordnik.format(time);
    }

    public static boolean getIsWeek(Map<TimeUnit, Long> dateMap) {
        boolean isWeek = false;

        if (dateMap != null) {
            isWeek = dateMap.get(TimeUnit.DAYS) > 0 && dateMap.get(TimeUnit.DAYS) <= 7;
        }

        return isWeek;
    }

    public static boolean getIsWeek(long time) {
        boolean isWeek = false;
        Map<TimeUnit, Long> dateMap = getDateAsMapWithTimeUnit(currentTime(), time);
        if (dateMap != null) {
            isWeek = dateMap.get(TimeUnit.DAYS) <= 7;
        }

        return isWeek;
    }

    public static String toDelayTime(long time) {

        if (DataUtil.isEmpty(time)) return null;

        long endTime = currentTime();
        long startTime = time;
        if (startTime > endTime) {
            startTime = endTime;
            endTime = time;
        }

        Period period = new Period(startTime, endTime);

        int years = period.getYears();
        if (years > 0) {
            return String.valueOf(years) + "Y ago";
        }

        int months = period.getMonths();
        if (months > 0) {
            return String.valueOf(months) + "M ago";
        }

        int weeks = period.getWeeks();
        if (weeks > 0) {
            return String.valueOf(weeks) + "W ago";
        }

        int days = period.getDays();
        if (days > 0) {
            return String.valueOf(days) + "D ago";
        }

        return "Today";
    }

    public static String toDelayTime(long newest, long oldest) {

        StringBuilder builder = new StringBuilder();

        Map<TimeUnit, Long> timeMap = getDateAsMapWithTimeUnit(newest, oldest);

        if (timeMap.containsKey(TimeUnit.HOURS)) {

            long hour = timeMap.get(TimeUnit.HOURS);

            if (hour > 0) {
                builder.append(hour);
                builder.append(" hour");
                builder.append(hour > 1 ? "s" : "");
            }
        }

        if (timeMap.containsKey(TimeUnit.MINUTES)) {
            long minute = timeMap.get(TimeUnit.MINUTES);

            if (minute > 0) {
                builder.append(" " + minute);
                builder.append(" minute");
                builder.append(minute > 1 ? "s" : "");
            }
        }

        builder.append(" ago");

        return builder.toString();
    }

    private static long toMilliFromTubeDuration(String duration) {
        if (DataUtil.isEmpty(duration)) {
            return 0;
        }

        // Example: "PT2M58S" -- ISO-8601
        // Not really a compliant parser
        Matcher m = LENGTH_PATTERN.matcher(duration);
        if (m.matches()) {
            String hr = m.group(1);
            String min = m.group(2);
            String sec = m.group(3);

            long d = 0;
            if (hr != null)
                d += Long.parseLong(hr) * 60 * 60;
            if (min != null)
                d += Long.parseLong(min) * 60;
            if (sec != null)
                d += Long.parseLong(sec);
            return d * 1000; // Milliseconds
        }

        return 0;
    }

    public static String toLocalFromDuration(String duration) {
        long milli = toMilliFromTubeDuration(duration);

        StringBuilder builder = new StringBuilder();

        Map<TimeUnit, Long> timeMap = getDateAsMapWithTimeUnit(milli);

        if (timeMap.containsKey(TimeUnit.HOURS)) {

            long hour = timeMap.get(TimeUnit.HOURS);

            if (hour > 0) {

                String hourValue = String.valueOf(hour);

                if (hourValue.length() == 1) {
                    hourValue = "0" + hourValue;
                }

                String commaAppend = builder.length() > 0 ? ":" : "";

                builder.append(commaAppend + hourValue);
            }
        }

        if (timeMap.containsKey(TimeUnit.MINUTES)) {
            long minute = timeMap.get(TimeUnit.MINUTES);

            if (minute > 0 || builder.length() > 0) {

                String minuteValue = String.valueOf(minute);

                if (minuteValue.length() == 1) {
                    minuteValue = "0" + minuteValue;
                }

                String commaAppend = builder.length() > 0 ? ":" : "";

                builder.append(commaAppend + minuteValue);
            }
        }

        if (timeMap.containsKey(TimeUnit.MINUTES)) {
            long second = timeMap.get(TimeUnit.SECONDS);

            if (second > 0 || builder.length() > 0) {

                String secondValue = String.valueOf(second);

                if (secondValue.length() == 1) {
                    secondValue = "0" + secondValue;
                }

                String commaAppend = builder.length() > 0 ? ":" : "";

                builder.append(commaAppend + secondValue);
            }
        }

        return builder.toString();
    }

    private static long time;
    private static long defaultConcurrentDelay = 2000L;

    public static boolean allowConcurrent() {
        return allowConcurrent(defaultConcurrentDelay);
    }

    public static boolean allowConcurrent(long delay) {
        if (!isDelayed(time, delay)) {
            return false;
        }

        time = currentTime();
        return true;
    }

    public interface TimerCallback {
        void onUpdate(long duration);
    }

    private static TimerCallback timerCallback;
    private static Timer timer;
    private static long duration;

    public static void startTimer(TimerCallback timerCallback) {
        stopTimer();
        TimeUtil.timerCallback = timerCallback;
        duration = 0L;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                duration += 1000L;
                if (TimeUtil.timerCallback != null) {
                    TimeUtil.timerCallback.onUpdate(duration);
                }
            }
        }, 0L, 1000L);
    }

    public static void stopTimer() {
        TimeUtil.timerCallback = null;
        duration = 0L;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
