package com.mission.apocalypse.controller.request;

import com.mission.apocalypse.constants.CalendarTypeEnum;
import com.mission.apocalypse.constants.GenderEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserQueryInfo {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("生日")
    private LocalDate birthday;

    @ApiModelProperty("出生时辰")
    private String birthHour;

    @ApiModelProperty("公历 Calendar 农历 Lunar")
    private CalendarTypeEnum dateType;

    @ApiModelProperty("性别 男Male 女:Female")
    private GenderEnum gender;
}
