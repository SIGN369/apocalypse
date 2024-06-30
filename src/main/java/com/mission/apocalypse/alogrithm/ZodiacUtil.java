package com.mission.apocalypse.alogrithm;

import com.mission.apocalypse.constants.ChineseZodiacEnum;
import com.mission.apocalypse.constants.ZodiacEnum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ZodiacUtil {
    public static void main(String[] args) {
        String inputDate = "2024-01-03";
        Locale locale = new Locale("zh"); // Change to new Locale("en") for English

        LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String zodiacSign = getZodiacSign(date, locale);
        String lunar = LunarSolarConverter.solarToLunar(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parse = LocalDate.parse(lunar, formatter);
        String chineseZodiac = getChineseZodiac(parse.getYear(), locale);

        System.out.println("Date of Birth: " + inputDate);
        System.out.println("Zodiac Sign: " + zodiacSign);
        System.out.println("Chinese Zodiac: " + chineseZodiac);
    }



    public static String getZodiacSign(LocalDate date, Locale locale) {
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();

        ZodiacEnum zodiac;

        if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) {
            zodiac = ZodiacEnum.AQUARIUS;
        } else if (month == 2 || month == 3 && day <= 20) {
            zodiac = ZodiacEnum.PISCES;
        } else if (month == 3 || month == 4 && day <= 19) {
            zodiac = ZodiacEnum.ARIES;
        } else if (month == 4 || month == 5 && day <= 20) {
            zodiac = ZodiacEnum.TAURUS;
        } else if (month == 5 || month == 6 && day <= 20) {
            zodiac = ZodiacEnum.GEMINI;
        } else if (month == 6 || month == 7 && day <= 22) {
            zodiac = ZodiacEnum.CANCER;
        } else if (month == 7 || month == 8 && day <= 22) {
            zodiac = ZodiacEnum.LEO;
        } else if (month == 8 || month == 9 && day <= 22) {
            zodiac = ZodiacEnum.VIRGO;
        } else if (month == 9 || month == 10 && day <= 22) {
            zodiac = ZodiacEnum.LIBRA;
        } else if (month == 10 || month == 11 && day <= 21) {
            zodiac = ZodiacEnum.SCORPIO;
        } else if (month == 11 || month == 12 && day <= 21) {
            zodiac = ZodiacEnum.SAGITTARIUS;
        } else {
            zodiac = ZodiacEnum.CAPRICORN;
        }

        return zodiac.getName(locale);
    }

    public static String getChineseZodiac(int year, Locale locale) {
        ChineseZodiacEnum[] zodiacArray = ChineseZodiacEnum.values();
        int startYear = 1900; // reference year, 1900 was the year of the Rat
        return zodiacArray[(year - startYear) % 12].getName(locale);
    }
}
