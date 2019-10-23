package com.red.star.macalline.act.admin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author AMGuo
 * @Description
 * @date 2018/03/20 15:56
 */
public class DateNewUtil {
    public static final String PACKED_DATE_FORMAT = "yyyyMMdd";
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
    public static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final SynchronizedDateFormat DATEFORMATPACKED = new SynchronizedDateFormat(new SimpleDateFormat("yyyyMMdd"));
    public static final SynchronizedDateFormat DATEFORMATISO = new SynchronizedDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    public static final SynchronizedDateFormat DATEFORMATDE = new SynchronizedDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
    public static final SynchronizedDateFormat DAYFORMATDE = new SynchronizedDateFormat(new SimpleDateFormat("dd.MM."));
    public static final SynchronizedDateFormat DATETIMEFORMATDESHORT = new SynchronizedDateFormat(new SimpleDateFormat("dd.MM.yyyy HH:mm"));
    public static final SynchronizedDateFormat DATETIMEFORMATISOSHORT = new SynchronizedDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
    public static final SynchronizedDateFormat DATETIMEFORMATDEMEDIUM = new SynchronizedDateFormat(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));
    public static final SynchronizedDateFormat DATETIMEFORMATISOMEDIUM = new SynchronizedDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    public static final SynchronizedDateFormat DATETIMEFORMATDELONG = new SynchronizedDateFormat(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSS"));
    public static final SynchronizedDateFormat DATETIMEFORMATISOLONG = new SynchronizedDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS"));
    public static final SynchronizedDateFormat TIMEFORMATSHORT = new SynchronizedDateFormat(new SimpleDateFormat("HH:mm"));
    public static final SynchronizedDateFormat TIMEFORMATMEDIUM = new SynchronizedDateFormat(new SimpleDateFormat("HH:mm:ss"));
    public static final SynchronizedDateFormat TIMEFORMATLONG = new SynchronizedDateFormat(new SimpleDateFormat("HH:mm:ss.SSS"));
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int SECONDS_PER_HOUR = 3600;
    public static final int SECONDS_PER_DAY = 86400;
    public static final int MILLIS_PER_SECOND = 1000;
    public static final int MILLIS_PER_MINUTE = 60000;
    public static final int MILLIS_PER_HOUR = 3600000;
    public static final int MILLIS_PER_DAY = 86400000;
    private static long referenceNanoTime;
    private static long referenceMilliTime;

    static {
        DATEFORMATPACKED.setLenient(true);
        DATEFORMATISO.setLenient(true);
        DATEFORMATDE.setLenient(true);
        DATETIMEFORMATDESHORT.setLenient(true);
        DATETIMEFORMATISOSHORT.setLenient(true);
        DATETIMEFORMATDEMEDIUM.setLenient(true);
        DATETIMEFORMATISOMEDIUM.setLenient(true);
        DATETIMEFORMATDELONG.setLenient(true);
        DATETIMEFORMATISOLONG.setLenient(true);
        TIMEFORMATSHORT.setLenient(true);
        TIMEFORMATMEDIUM.setLenient(true);
        TIMEFORMATLONG.setLenient(true);
        referenceNanoTime = System.nanoTime();
        referenceMilliTime = System.currentTimeMillis();
    }

    public DateNewUtil() {
    }

    public static Date parseIsoString(String s) throws ParseException {
        if (s != null && !"".equals(s)) {
            Date result = null;

            try {
                result = DATETIMEFORMATISOMEDIUM.parse(s);
            } catch (ParseException var3) {
                try {
                    result = DATEFORMATISO.parse(s);
                } catch (ParseException var2) {
                    ;
                }
            }

            if (result == null) {
                throw new ParseException("Could not convert String '" + s + "' to Date.", 0);
            } else {
                return result;
            }
        } else {
            return null;
        }
    }

    public static Date parseString(String s, String format) throws ParseException {
        if (s != null && !"".equals(s)) {
            DateFormat df = new SimpleDateFormat(format);
            df.setLenient(true);
            return df.parse(s);
        } else {
            return null;
        }
    }

    public static String formatSeconds(long seconds) {
        if (seconds == 0L) {
            return "0 seconds";
        } else {
            boolean minus = false;
            if (seconds < 0L) {
                minus = true;
                seconds = -seconds;
            }

            long x = seconds;
            seconds %= 60L;
            x /= 60L;
            long minutes = x % 60L;
            x /= 60L;
            long hours = x % 24L;
            x /= 24L;
            StringBuilder builder = new StringBuilder();
            if (minus) {
                builder.append("-");
            }

            boolean firstItem = true;
            if (x != 0L) {
                firstItem = false;
                builder.append(x).append(" day");
                if (x != 1L) {
                    builder.append("s");
                }
            }

            if (hours != 0L) {
                if (!firstItem) {
                    builder.append(", ");
                } else {
                    firstItem = false;
                }

                builder.append(hours).append(" hour");
                if (hours != 1L) {
                    builder.append("s");
                }
            }

            if (minutes != 0L) {
                if (!firstItem) {
                    builder.append(", ");
                } else {
                    firstItem = false;
                }

                builder.append(minutes).append(" minute");
                if (minutes != 1L) {
                    builder.append("s");
                }
            }

            if (seconds != 0L) {
                if (!firstItem) {
                    builder.append(", ");
                } else {
                    firstItem = false;
                }

                builder.append(seconds).append(" second");
                if (seconds != 1L) {
                    builder.append("s");
                }
            }

            return builder.toString();
        }
    }

    public static Date parseAnyString(String s) throws ParseException {
        if (s != null && !"".equals(s)) {
            Date result = null;

            try {
                result = DATETIMEFORMATISOLONG.parse(s);
            } catch (ParseException var13) {
                try {
                    result = DATETIMEFORMATDELONG.parse(s);
                } catch (ParseException var12) {
                    try {
                        result = DATETIMEFORMATISOMEDIUM.parse(s);
                    } catch (ParseException var11) {
                        try {
                            result = DATETIMEFORMATDEMEDIUM.parse(s);
                        } catch (ParseException var10) {
                            try {
                                result = DATETIMEFORMATISOSHORT.parse(s);
                            } catch (ParseException var9) {
                                try {
                                    result = DATETIMEFORMATDESHORT.parse(s);
                                } catch (ParseException var8) {
                                    try {
                                        result = DATEFORMATISO.parse(s);
                                    } catch (ParseException var7) {
                                        try {
                                            result = DATEFORMATDE.parse(s);
                                        } catch (ParseException var6) {
                                            try {
                                                result = TIMEFORMATLONG.parse(s);
                                            } catch (ParseException var5) {
                                                try {
                                                    result = TIMEFORMATMEDIUM.parse(s);
                                                } catch (ParseException var4) {
                                                    try {
                                                        result = TIMEFORMATSHORT.parse(s);
                                                    } catch (ParseException var3) {
                                                        try {
                                                            result = DATEFORMATPACKED.parse(s);
                                                        } catch (ParseException var2) {
                                                            ;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (result == null) {
                throw new ParseException("Could not convert String '" + s + "' to Date.", 0);
            } else {
                return result;
            }
        } else {
            return null;
        }
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
            return sfDate.format(date);
        }
    }

    public static String formatDate(Date date, String pattern,Locale locale) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sfDate = new SimpleDateFormat(pattern, locale);
            return sfDate.format(date);
        }
    }

    public static String formatDate(Date date) {
        return date == null ? null : DATEFORMATISO.format(date);
    }

    public static String formatDateTime(Date date) {
        return date == null ? null : DATETIMEFORMATISOMEDIUM.format(date);
    }

    public static Date incDay(Date date, int days) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(5, days);
        return cal.getTime();
    }

    public static Date incMonth(Date date, int months) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(2, months);
        return cal.getTime();
    }

    public static Date incYear(Date date, int years) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(1, years);
        return cal.getTime();
    }

    public static Date incHour(Date date, int hours) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(10, hours);
        return cal.getTime();
    }

    public static Date incMinute(Date date, int minutes) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(12, minutes);
        return cal.getTime();
    }

    public static Date incSecond(Date date, int seconds) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(13, seconds);
        return cal.getTime();
    }

    public static Date getCurrentDate() {
        return truncateToDate(new Date(System.currentTimeMillis()));
    }

    public static Date today() {
        return getCurrentDate();
    }

    public static Date getCurrentDateTime() {
        return new Date(System.currentTimeMillis());
    }

    public static Date now() {
        return getCurrentDateTime();
    }

    public static Date getCurrentDateTimeMillis() {
        long currentNanos = System.nanoTime();
        long deltaNanos = currentNanos - referenceNanoTime;
        long currentMillis = referenceMilliTime + deltaNanos / 1000000L;
        return new Date(currentMillis);
    }

    public static Date truncateToDate(Date d) {
        if (d instanceof java.sql.Date) {
            return d;
        } else {
            d = (Date) d.clone();
            d.setHours(0);
            d.setMinutes(0);
            d.setSeconds(0);
            d.setTime(d.getTime() / 1000L * 1000L);
            return d;
        }
    }

    public static Date truncateToDateTime(Date d) {
        long millis = d.getTime();
        millis -= millis % 1000L;
        return new Date(millis);
    }

    public static String getCurrentDateString() {
        return formatDate(new Date(System.currentTimeMillis()));
    }

    public static long millisBetween(Date startDate, Date endDate) {
        return endDate.getTime() - startDate.getTime();
    }

    public static long secondsBetween(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) / 1000L;
    }

    public static long minutesBetween(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) / 60000L;
    }

    public static long hoursBetween(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) / 3600000L;
    }

    public static long daysBetween(Date firstDate, Date secondDate) {
        long firstSeconds = truncateToDate(firstDate).getTime() / 1000L;
        long secondSeconds = truncateToDate(secondDate).getTime() / 1000L;
        long difference = secondSeconds - firstSeconds;
        if (difference >= 0L) {
            difference += 43200L;
        } else {
            difference -= 43200L;
        }

        difference /= 86400L;
        return difference;
    }

    public static long monthsBetween(Date firstDate, Date secondDate) {
        return (long) ((secondDate.getYear() - firstDate.getYear()) * 12 + (secondDate.getMonth() - firstDate.getMonth()));
    }

    public static long yearsBetween(Date firstDate, Date secondDate) {
        return (long) (secondDate.getYear() - firstDate.getYear());
    }

    public static boolean isWeekEnd(Date d) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        int dayOfWeek = cal.get(7);
        return dayOfWeek == 7 || dayOfWeek == 1;
    }

    public static boolean isSaturday(Date d) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        int dayOfWeek = cal.get(7);
        return dayOfWeek == 7;
    }

    public static boolean isSunday(Date d) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        int dayOfWeek = cal.get(7);
        return dayOfWeek == 1;
    }

    public static int getWeekday(Date d) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        return cal.get(7);
    }

    public static Date min(Date a, Date b) {
        if (a != null && b != null) {
            return a.before(b) ? a : b;
        } else {
            return null;
        }
    }

    public static Date max(Date a, Date b) {
        if (a == null && b != null) {
            return b;
        } else if (a != null && b == null) {
            return a;
        } else if (a == null && b == null) {
            return null;
        } else {
            return a.before(b) ? b : a;
        }
    }

    public static boolean isBeforeNoon(Date d) {
        return d.getHours() < 12;
    }

    public static boolean isAfterNoon(Date d) {
        return d.getHours() >= 12;
    }

    public static Date getFirstdayOfMonth(Date date) throws ParseException {
        String dateString = formatDate(date, "yyyy-MM");
        dateString = dateString + "-01";
        return parseString(dateString, "yyyy-MM-dd");
    }

    public static Date getLastdayOfMonth(Date date) throws ParseException {
        String dateString = formatDate(date, "yyyy-MM");
        Calendar calCurr = GregorianCalendar.getInstance();
        calCurr.setTime(date);
        dateString = dateString + "-" + calCurr.getActualMaximum(5);
        return parseString(dateString, "yyyy-MM-dd");
    }
}
