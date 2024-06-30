package com.mission.apocalypse.alogrithm;

import com.mission.apocalypse.controller.request.UserQueryInfo;
import com.mission.apocalypse.controller.response.LightFateResponse;

import java.util.HashMap;
import java.util.Map;

public class PanelAlgUtil {


    // 农历每个月的天数表，平年和闰年
    private static final int[] LUNAR_MONTH_DAYS = {0, 29, 30};
    // 1995年农历数据，假设平年，每月天数以及闰月信息
    private static final Map<Integer, Integer[]> LUNAR_YEAR_DATA = new HashMap<>();
    static {
        // 1995年农历数据：每月天数，闰月为0表示没有闰月
        LUNAR_YEAR_DATA.put(1995, new Integer[]{0, 30, 29, 30, 29, 30, 29, 29, 30, 29, 30, 29, 30});
    }

    public static LightFateResponse cal (UserQueryInfo userQueryInfo) {
        LightFateResponse lightFateResponse = new LightFateResponse();

        return lightFateResponse;
    }


    public static void main(String[] args) {
        // 输入农历生日
        int lunarYear = 1998;
        int lunarMonth = 4;
        int lunarDay = 16;

        // 转换为公历
        String gregorianDate = lunarToGregorian(lunarYear, lunarMonth, lunarDay);

        // 输出公历生日
        System.out.println("公历生日: " + gregorianDate);
    }

    public static String lunarToGregorian(int year, int month, int day) {
        // 基准日期：1995年农历新年的公历日期
        int baseYear = 1995;
        int baseMonth = 1;
        int baseDay = 31;

        // 计算农历日期到农历年的天数
        int totalDays = 0;
        Integer[] lunarMonths = LUNAR_YEAR_DATA.get(year);
        for (int i = 1; i < month; i++) {
            totalDays += lunarMonths[i];
        }
        totalDays += day - 1;

        // 将农历天数转换为公历日期
        return addDays(baseYear, baseMonth, baseDay, totalDays);
    }

    public static String addDays(int year, int month, int day, int days) {
        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        while (days > 0) {
            int daysInMonth = monthDays[month - 1];
            // 闰年处理
            if (month == 2 && isLeapYear(year)) {
                daysInMonth = 29;
            }
            if (days < daysInMonth - day + 1) {
                day += days;
                days = 0;
            } else {
                days -= (daysInMonth - day + 1);
                day = 1;
                if (month == 12) {
                    month = 1;
                    year++;
                } else {
                    month++;
                }
            }
        }

        return String.format("%d-%02d-%02d", year, month, day);
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
