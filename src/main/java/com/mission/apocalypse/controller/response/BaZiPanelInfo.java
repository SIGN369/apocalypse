package com.mission.apocalypse.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("八字排盘")
@Data
public class BaZiPanelInfo {

    /**
     * 农历生日：年-月-日-时
     * 	1995年	5月	15日	亥时
     */
    private String year;
    private String month;
    private String day;
    private String hour;

    /**
     * 乾坤造：年月日食的天干地支表达方式
     * 乙亥年 辛巳月	丙午日	己亥时
     */
    private String yearHeavenEarth;
    private String monthHeavenEarth;
    private String dayHeavenEarth;
    private String hourHeavenEarth;

    /**
     *  五行	木水	金火	火火	土水
     */
    private String yearWuXing;
    private String monthWuXing;
    private String dayWuXing;
    private String hourWuXing;

    /**
     * 十神  正印 正财 日主 伤官
     */
    private String yearTenGod;
    private String monthTenGod;
    private String dayTenGod;
    private String hourTenGod;


    /**
     *  纳音	山头火	白蜡金	天河水	平地木
     */
    private String yearNaYin;
    private String monthNaYin;
    private String dayNaYin;
    private String hourNaYin;







}
