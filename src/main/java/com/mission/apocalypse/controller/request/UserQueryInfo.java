package com.mission.apocalypse.controller.request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserQueryInfo {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("生日")
    private String birthday;

    @ApiModelProperty("出生时辰")
    private String birthHour;

    @ApiModelProperty("公历 Calendar 农历 Lunar")
    private String dateType;

    @ApiModelProperty("性别 男Male 女:Female")
    private String gender;
}
