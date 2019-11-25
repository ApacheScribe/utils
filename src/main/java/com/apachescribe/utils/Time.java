package com.apachescribe.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.util.Calendar;
import java.text.ParseException;
import org.joda.time.DateTime;
import org.joda.time.Months;

public class Time {

    public static String getTimestamp() {
        Date date = new Date();
        return new Timestamp(date.getTime()).toString();
    }

    public static String today() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(new Date());
        return date;
    }

    public static String toStringDate(Date ydate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(ydate);
        return date;
    }

    public static String newDate(String dateStr) throws ParseException {
        // String dateStr = "Mon Jun 18 00:00:00 IST 2012";
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date date = (Date) formatter.parse(dateStr);
        // System.out.println(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH)
        // + 1) + "/" + cal.get(Calendar.YEAR);
        String yyear = cal.get(Calendar.YEAR) + "";
        String ymonth = (cal.get(Calendar.MONTH) + 1) + "";
        String ydate = (cal.get(Calendar.DATE)) + "";

        if (ymonth.length() < 2) {
            ymonth = "0" + ymonth;
        }
        if (ydate.length() < 2) {
            ydate = "0" + ydate;
        }

        String yformatedDate = yyear.trim() + ymonth.trim() + ydate.trim();
        return yformatedDate;
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    public static String increment(String number) {
        Pattern compile = Pattern.compile("^(.*?)([9Z]*)$");
        Matcher matcher = compile.matcher(number);
        String left = "";
        String right = "";
        if (matcher.matches()) {
            left = matcher.group(1);
            right = matcher.group(2);
        }
        number = !left.isEmpty() ? Long.toString(Long.parseLong(left, 36) + 1, 36) : "";
        number += right.replace("Z", "A").replace("9", "0");
        return number.toUpperCase();
    }

    public static String getDateDifferenceInDDMMYYYY(Date from, Date to) {
        Calendar fromDate = Calendar.getInstance();
        Calendar toDate = Calendar.getInstance();
        fromDate.setTime(from);
        toDate.setTime(to);
        int increment = 0;
        int year, month, day;
        // System.out.println(fromDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        if (fromDate.get(Calendar.DAY_OF_MONTH) > toDate.get(Calendar.DAY_OF_MONTH)) {
            increment = fromDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        // System.out.println("increment" + increment);
        // DAY CALCULATION
        if (increment != 0) {
            day = (toDate.get(Calendar.DAY_OF_MONTH) + increment) - fromDate.get(Calendar.DAY_OF_MONTH);
            increment = 1;
        } else {
            day = toDate.get(Calendar.DAY_OF_MONTH) - fromDate.get(Calendar.DAY_OF_MONTH);
        }

        // MONTH CALCULATION
        if ((fromDate.get(Calendar.MONTH) + increment) > toDate.get(Calendar.MONTH)) {
            month = (toDate.get(Calendar.MONTH) + 12) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 1;
        } else {
            month = (toDate.get(Calendar.MONTH)) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 0;
        }

        // YEAR CALCULATION
        year = toDate.get(Calendar.YEAR) - (fromDate.get(Calendar.YEAR) + increment);
        // return year + "\tYears\t\t" + month + "\tMonths\t\t" + day + "\tDays";
        return year + "Y " + month + "M " + day + "D";

    }

    public static int monthDiff(Date startDate, Date endDate) throws ParseException {
        // System.out.println("Starting Date " + Utils.newDate(startDate.toString()));

        int months;
        int year1 = Integer.parseInt(newDate(startDate.toString()).substring(0, 4));
        // System.out.println("year1 " + year1);
        int month1 = Integer.parseInt(newDate(startDate.toString()).substring(4, 6));
        int date1 = Integer.parseInt(newDate(startDate.toString()).substring(6, 8));

        int year2 = Integer.parseInt(newDate(startDate.toString()).substring(0, 4));
        int month2 = Integer.parseInt(newDate(startDate.toString()).substring(4, 6));
        int date2 = Integer.parseInt(newDate(startDate.toString()).substring(6, 8));

        DateTime init = new DateTime().withDate(year1, month1, date1).withTime(0, 0, 0, 0);
        DateTime end = new DateTime().withDate(year2, month2, date2).withTime(0, 0, 0, 0);

        months = Months.monthsBetween(end, init).getMonths();

        return months;
    }

    public static XMLGregorianCalendar convertStringToXmlGregorian() throws DatatypeConfigurationException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date = sdf.parse(dateString);
            GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
            gc.setTime(date);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (ParseException e) {
            // Optimize exception handling
            System.out.print(e.getMessage());
            return null;
        }
    }
}