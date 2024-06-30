package com.mission.apocalypse.alogrithm;

import com.github.heqiao2010.lunar.LunarCalendar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Scanner;

/**
 * 年份（1900-2099）
 */
public class LunarCalendarConverter {
    private static final long[] LUNAR_INFO = new long[]{
            0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
            0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
            0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
            0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
            0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
            0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
            0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
            0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
            0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
            0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
            0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
            0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
            0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
            0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
            0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0
    };

//    static final String[] LUNAR_MONTH = {
//            "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"
//    };

    static final int[] LUNAR_MONTH = {
            1, 2, 3, 4,5, 6, 7, 8, 9, 10, 11, 12
    };

//    static final String[] LUNAR_DAY = {
//            "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十",
//            "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
//            "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"
//    };

    static final int[] LUNAR_DAY = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    };

    public static String convertToLunar(int year, int month, int day) {
        validateDate(year, month, day);

        int offset = (int) (getGregorianDays(year, month, day) - getGregorianDays(1900, 1, 31));

        int lunarYear = 1900;
        int lunarMonth = 1;
        int lunarDay;
        boolean isLeap = false;

        int daysInLunarYear = getLunarYearDays(lunarYear);
        while (offset >= daysInLunarYear) {
            offset -= daysInLunarYear;
            lunarYear++;
            if (lunarYear > 2099) {
                throw new IllegalArgumentException("Date out of range (2099+)");
            }
            daysInLunarYear = getLunarYearDays(lunarYear);
        }

        int leapMonth = getLeapMonth(lunarYear);
        int daysInLunarMonth;

        for (lunarMonth = 1; lunarMonth <= 12; lunarMonth++) {
            if (leapMonth > 0 && lunarMonth == leapMonth + 1 && !isLeap) {
                isLeap = true;
                daysInLunarMonth = getLeapDays(lunarYear);
            } else {
                daysInLunarMonth = getLunarMonthDays(lunarYear, lunarMonth);
                isLeap = false;
            }

            if (offset < daysInLunarMonth) {
                break;
            }
            offset -= daysInLunarMonth;
        }

        lunarDay = offset + 1;

        String monthStr = (isLeap ? "闰" : "") + LUNAR_MONTH[lunarMonth - 1] + "月";
        int dayStr = LUNAR_DAY[lunarDay - 1];

        return lunarYear + "年" + monthStr + dayStr;
    }

    private static void validateDate(int year, int month, int day) {
        if (year < 1900 || year > 2099) {
            throw new IllegalArgumentException("Year must be between 1900 and 2099");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isGregorianLeapYear(year)) {
            monthDays[1] = 29;
        }
        if (day < 1 || day > monthDays[month - 1]) {
            throw new IllegalArgumentException("Invalid day for the given month and year");
        }
    }

    private static long getGregorianDays(int year, int month, int day) {
        int[] monthDays = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        long totalDays = (year - 1) * 365L + (year - 1) / 4 - (year - 1) / 100 + (year - 1) / 400;
        totalDays += monthDays[month - 1] + day;
        if (month > 2 && isGregorianLeapYear(year)) {
            totalDays++;
        }
        return totalDays;
    }

    static boolean isGregorianLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    private static int getLunarYearDays(int year) {
        int sum = 348;
        for (int i = 0x8000; i > 0x8; i >>= 1) {
            sum += ((LUNAR_INFO[year - 1900] & i) != 0) ? 1 : 0;
        }
        return sum + getLeapDays(year);
    }

    private static int getLeapMonth(int year) {
        return (int) (LUNAR_INFO[year - 1900] & 0xf);
    }

    private static int getLeapDays(int year) {
        if (getLeapMonth(year) != 0) {
            return ((LUNAR_INFO[year - 1900] & 0x10000) != 0) ? 30 : 29;
        }
        return 0;
    }

    private static int getLunarMonthDays(int year, int month) {
        return ((LUNAR_INFO[year - 1900] & (0x10000 >> month)) != 0) ? 30 : 29;
    }

    public static String convertToLunar(String gregorianDateStr) {
        try {
            LocalDate gregorianDate = LocalDate.parse(gregorianDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            return convertToLunar(gregorianDate.getYear(), gregorianDate.getMonthValue(), gregorianDate.getDayOfMonth());
        } catch (DateTimeParseException e) {
            return "输入日期格式错误,请使用YYYY-MM-DD格式";
        }
    }


    public static void main(String[] args) {
        String gregorianDate = "1995-05-15";
        String lunarDate = convertToLunar(gregorianDate);
        System.out.println("公历日期: " + gregorianDate);
        System.out.println("农历日期: " + lunarDate);
    }



}