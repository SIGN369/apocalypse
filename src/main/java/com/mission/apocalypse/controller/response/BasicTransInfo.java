package com.mission.apocalypse.controller.response;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 测算者信息
 */
@Data
@ApiModel("测算者信息")
public class BasicTransInfo {

    @ApiModelProperty("公历生日")
    private LocalDate solarBirth;

    @ApiModelProperty("农历生日")
    private LocalDate lunarBirth;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("岁数")
    private Integer age;

    @ApiModelProperty("星座")
    private String constellation;

    @ApiModelProperty("属相")
    private String zodiac;



}
