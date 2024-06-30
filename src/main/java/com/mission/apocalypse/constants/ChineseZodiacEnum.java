package com.mission.apocalypse.constants;

import lombok.Getter;

import java.util.Locale;

/**
 * 十二生肖
 */
@Getter
public enum ChineseZodiacEnum {
    RAT("鼠", "Rat"),
    OX("牛", "Ox"),
    TIGER("虎", "Tiger"),
    RABBIT("兔", "Rabbit"),
    DRAGON("龙", "Dragon"),
    SNAKE("蛇", "Snake"),
    HORSE("马", "Horse"),
    GOAT("羊", "Goat"),
    MONKEY("猴", "Monkey"),
    ROOSTER("鸡", "Rooster"),
    DOG("狗", "Dog"),
    PIG("猪", "Pig");

    private final String chineseName;
    private final String englishName;

    ChineseZodiacEnum(String chineseName, String englishName) {
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
