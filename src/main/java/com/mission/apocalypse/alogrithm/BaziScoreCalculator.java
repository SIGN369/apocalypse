package com.mission.apocalypse.alogrithm;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BaziScoreCalculator {
    private static final Map<String, Integer> TIANGAN_INDEX = new HashMap<>();
    private static final Map<String, Integer> DIZHI_INDEX = new HashMap<>();
    /**
     * 天干
     */
    private static final String[] TIANGAN = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    /**
     * 地支
     */
    private static final String[] DIZHI = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    private static final Map<String, String> WUXING = new HashMap<>();

    static {
        for (int i = 0; i < TIANGAN.length; i++) {
            TIANGAN_INDEX.put(TIANGAN[i], i);
        }
        for (int i = 0; i < DIZHI.length; i++) {
            DIZHI_INDEX.put(DIZHI[i], i);
        }
        WUXING.put("甲", "木"); WUXING.put("乙", "木");
        WUXING.put("丙", "火"); WUXING.put("丁", "火");
        WUXING.put("戊", "土"); WUXING.put("己", "土");
        WUXING.put("庚", "金"); WUXING.put("辛", "金");
        WUXING.put("壬", "水"); WUXING.put("癸", "水");
        WUXING.put("子", "水"); WUXING.put("丑", "土");
        WUXING.put("寅", "木"); WUXING.put("卯", "木");
        WUXING.put("辰", "土"); WUXING.put("巳", "火");
        WUXING.put("午", "火"); WUXING.put("未", "土");
        WUXING.put("申", "金"); WUXING.put("酉", "金");
        WUXING.put("戌", "土"); WUXING.put("亥", "水");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入第一个人的出生年：");
        int year1 = scanner.nextInt();
        System.out.print("请输入第一个人的出生月：");
        int month1 = scanner.nextInt();
        System.out.print("请输入第一个人的出生日：");
        int day1 = scanner.nextInt();

        System.out.print("请输入第二个人的出生年：");
        int year2 = scanner.nextInt();
        System.out.print("请输入第二个人的出生月：");
        int month2 = scanner.nextInt();
        System.out.print("请输入第二个人的出生日：");
        int day2 = scanner.nextInt();

        String[] bazi1 = calculateBazi(year1, month1, day1);
        String[] bazi2 = calculateBazi(year2, month2, day2);

        int score = calculateScore(bazi1, bazi2);
        System.out.println("两个生日的匹配得分为：" + score + " 分");
    }

    private static String[] calculateBazi(int year, int month, int day) {
        // 简化版本，只计算年柱、月柱和日柱，时柱略
        String yearPillar = calculateYearPillar(year);
        String monthPillar = calculateMonthPillar(year, month);
        String dayPillar = calculateDayPillar(year, month, day);
        return new String[]{yearPillar, monthPillar, dayPillar};
    }

    private static String calculateYearPillar(int year) {
        int tianganIndex = (year - 4) % 10;
        int dizhiIndex = (year - 4) % 12;
        return TIANGAN[tianganIndex] + DIZHI[dizhiIndex];
    }

    private static String calculateMonthPillar(int year, int month) {
        int baseIndex = ((year % 5) * 2 + 3) % 10;
        int dizhiIndex = (month + 1) % 12;
        return TIANGAN[(baseIndex + month - 1) % 10] + DIZHI[dizhiIndex];
    }

    private static String calculateDayPillar(int year, int month, int day) {
        // 简化算法，不考虑时辰影响
        LocalDate baseDate = LocalDate.of(1900, 1, 1); // 假设基准日
        LocalDate birthDate = LocalDate.of(year, month, day);
        long daysBetween = ChronoUnit.DAYS.between(baseDate, birthDate);
        int tianganIndex = (int) (daysBetween % 10);
        int dizhiIndex = (int) (daysBetween % 12);
        return TIANGAN[tianganIndex] + DIZHI[dizhiIndex];
    }

    private static int calculateScore(String[] bazi1, String[] bazi2) {
        int score = 0;
        score += calculateWuxingBalanceScore(bazi1, bazi2);
        score += calculateHeChuScore(bazi1, bazi2);
        score += calculateXingGeScore(bazi1, bazi2);
        score += calculateDaYunScore(bazi1, bazi2);
        return score;
    }

    private static int calculateWuxingBalanceScore(String[] bazi1, String[] bazi2) {
        int score = 0;
        Map<String, Integer> count1 = countWuxing(bazi1);
        Map<String, Integer> count2 = countWuxing(bazi2);

        for (String wuxing : WUXING.values()) {
            int total = count1.getOrDefault(wuxing, 0) + count2.getOrDefault(wuxing, 0);
            if (total > 1 && total < 5) {
                score += 6; // 总分30分，平均分配
            }
        }
        return score;
    }

    private static Map<String, Integer> countWuxing(String[] bazi) {
        Map<String, Integer> count = new HashMap<>();
        for (String pillar : bazi) {
            String tiangan = pillar.substring(0, 1);
            String dizhi = pillar.substring(1);
            count.put(WUXING.get(tiangan), count.getOrDefault(WUXING.get(tiangan), 0) + 1);
            count.put(WUXING.get(dizhi), count.getOrDefault(WUXING.get(dizhi), 0) + 1);
        }
        return count;
    }

    private static int calculateHeChuScore(String[] bazi1, String[] bazi2) {
        int score = 0;
        // 天干地支相合逻辑（简化版本）
        for (int i = 0; i < bazi1.length; i++) {
            String t1 = bazi1[i].substring(0, 1);
            String d1 = bazi1[i].substring(1);
            String t2 = bazi2[i].substring(0, 1);
            String d2 = bazi2[i].substring(1);
            if (TIANGAN_INDEX.get(t1) % 2 == TIANGAN_INDEX.get(t2) % 2) score += 5;
            if (DIZHI_INDEX.get(d1) % 2 == DIZHI_INDEX.get(d2) % 2) score += 5;
        }
        return score;
    }

    private static int calculateXingGeScore(String[] bazi1, String[] bazi2) {
        int score = 0;
        // 性格互补逻辑（简化版本）
        if (bazi1[2].substring(0, 1).equals(bazi2[2].substring(0, 1))) {
            score += 20;
        }
        return score;
    }

    private static int calculateDaYunScore(String[] bazi1, String[] bazi2) {
        int score = 0;
        // 大运匹配逻辑（简化版本）
        score += 10;
        return score;
    }
}

