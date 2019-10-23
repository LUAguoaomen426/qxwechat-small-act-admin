package com.red.star.macalline.act.admin.util;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author AMGuo
 * @Description
 * @date 2018/03/20 15:57
 */
public class SynchronizedDateFormat {
    private final SimpleDateFormat dateformat;

    public SynchronizedDateFormat(SimpleDateFormat df) {
        this.dateformat = df;
    }

    public synchronized StringBuffer format(Date date, StringBuffer stringbuffer, FieldPosition fieldposition) {
        return this.dateformat.format(date, stringbuffer, fieldposition);
    }

    public synchronized StringBuffer format(Object obj, StringBuffer stringbuffer, FieldPosition fieldposition) {
        return this.dateformat.format(obj, stringbuffer, fieldposition);
    }

    public synchronized AttributedCharacterIterator formatToCharacterIterator(Object obj) {
        return this.dateformat.formatToCharacterIterator(obj);
    }

    public synchronized Calendar getCalendar() {
        return this.dateformat.getCalendar();
    }

    public synchronized NumberFormat getNumberFormat() {
        return this.dateformat.getNumberFormat();
    }

    public synchronized TimeZone getTimeZone() {
        return this.dateformat.getTimeZone();
    }

    public synchronized boolean isLenient() {
        return this.dateformat.isLenient();
    }

    public synchronized Date parse(String s, ParsePosition parseposition) {
        return this.dateformat.parse(s, parseposition);
    }

    public synchronized Date parse(String s) throws ParseException {
        return this.dateformat.parse(s);
    }

    public synchronized Object parseObject(String s, ParsePosition parseposition) {
        return this.dateformat.parseObject(s, parseposition);
    }

    public synchronized Object parseObject(String s) throws ParseException {
        return this.dateformat.parseObject(s);
    }

    public synchronized void setCalendar(Calendar calendar1) {
        this.dateformat.setCalendar(calendar1);
    }

    public synchronized void setLenient(boolean flag) {
        this.dateformat.setLenient(flag);
    }

    public synchronized void setNumberFormat(NumberFormat numberformat) {
        this.dateformat.setNumberFormat(numberformat);
    }

    public synchronized void setTimeZone(TimeZone timezone) {
        this.dateformat.setTimeZone(timezone);
    }

    public synchronized void applyLocalizedPattern(String s) {
        this.dateformat.applyLocalizedPattern(s);
    }

    public synchronized void applyPattern(String s) {
        this.dateformat.applyPattern(s);
    }

    public synchronized Date get2DigitYearStart() {
        return this.dateformat.get2DigitYearStart();
    }

    public synchronized DateFormatSymbols getDateFormatSymbols() {
        return this.dateformat.getDateFormatSymbols();
    }

    public synchronized void set2DigitYearStart(Date date) {
        this.dateformat.set2DigitYearStart(date);
    }

    public synchronized void setDateFormatSymbols(DateFormatSymbols dateformatsymbols) {
        this.dateformat.setDateFormatSymbols(dateformatsymbols);
    }

    public synchronized String toLocalizedPattern() {
        return this.dateformat.toLocalizedPattern();
    }

    public synchronized String toPattern() {
        return this.dateformat.toPattern();
    }

    public final synchronized String format(Date obj) {
        return this.dateformat.format(obj);
    }
}

