package com.mission.apocalypse.alogrithm;

import com.mission.apocalypse.constants.CalendarTypeEnum;
import com.mission.apocalypse.constants.GenderEnum;
import com.mission.apocalypse.controller.request.UserQueryInfo;
import com.mission.apocalypse.controller.response.BasicTransInfo;
import com.mission.apocalypse.controller.response.LightFateResponse;
import com.mission.apocalypse.util.DateUtils;
import com.nextengine.zeus.tk.json.JsonEx;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
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
        LocalDate birthday = userQueryInfo.getBirthday();
        GenderEnum gender = userQueryInfo.getGender();
        Locale locale = new Locale("zh");
        BasicTransInfo basicTransInfo = new BasicTransInfo();
        if (CalendarTypeEnum.SOLAR.name().equals(userQueryInfo.getDateType().name())) {
            String lunarBirth = LunarSolarConverter.solarToLunar(birthday.getYear(), birthday.getMonthValue(), birthday.getDayOfMonth());
            LocalDate lunarBirth1 = DateUtils.parseDate(lunarBirth);
            basicTransInfo.setLunarBirth(lunarBirth1);
            basicTransInfo.setSolarBirth(birthday);
            basicTransInfo.setConstellation(ZodiacUtil.getZodiacSign(birthday,locale));
        }
        if (CalendarTypeEnum.LUNAR.name().equals(userQueryInfo.getDateType().name())) {
            String solarBirth = LunarSolarConverter.lunarToSolar(birthday.getYear(), birthday.getMonthValue(), birthday.getDayOfMonth());
            LocalDate solarBirthDate = DateUtils.parseDate(solarBirth);
            basicTransInfo.setSolarBirth(solarBirthDate);
            basicTransInfo.setLunarBirth(birthday);
            basicTransInfo.setConstellation(ZodiacUtil.getZodiacSign(solarBirthDate,locale));
        }
        basicTransInfo.setGender(gender.name());
        log.info(">>>>>>>>> basicTransInfo cal end :{}", JsonEx.toJsonString(basicTransInfo));

        lightFateResponse.setBasicTransInfo(basicTransInfo);
        return lightFateResponse;
    }


    public static void main(String[] args) {
        UserQueryInfo userQueryInfo = new UserQueryInfo();
        LocalDate localDate = LocalDate.of(1995, 05, 15);
        userQueryInfo.setBirthday(localDate);
        userQueryInfo.setGender(GenderEnum.MAIL);
        userQueryInfo.setDateType(CalendarTypeEnum.SOLAR);
        cal(userQueryInfo);
    }



}
