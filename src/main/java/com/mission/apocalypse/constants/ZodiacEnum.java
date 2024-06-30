package com.mission.apocalypse.constants;

import lombok.Getter;

import java.util.Locale;

/**
 * 十二星座
 */
@Getter
public enum ZodiacEnum {
    AQUARIUS("水瓶座", "Aquarius"),
    PISCES("双鱼座", "Pisces"),
    ARIES("白羊座", "Aries"),
    TAURUS("金牛座", "Taurus"),
    GEMINI("双子座", "Gemini"),
    CANCER("巨蟹座", "Cancer"),
    LEO("狮子座", "Leo"),
    VIRGO("处女座", "Virgo"),
    LIBRA("天秤座", "Libra"),
    SCORPIO("天蝎座", "Scorpio"),
    SAGITTARIUS("射手座", "Sagittarius"),
    CAPRICORN("摩羯座", "Capricorn");

    private final String chineseName;
    private final String englishName;

    ZodiacEnum(String chineseName, String englishName) {
        this.chineseName = chineseName;
        this.englishName = englishName;
    }

    public String getName(Locale locale) {
        if (locale.getLanguage().equals("zh")) {
            return chineseName;
        } else {
            return englishName;
        }
    }
}
