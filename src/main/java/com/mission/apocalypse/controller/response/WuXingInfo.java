package com.mission.apocalypse.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("五行的")
@Data
public class WuXingInfo {

    @ApiModelProperty("金")
    private Integer metal;

    @ApiModelProperty("木")
    private Integer wood;

    @ApiModelProperty("水")
    private Integer water;

    @ApiModelProperty("火")
    private Integer fire;

    @ApiModelProperty("土")
    private Integer soil;

    @ApiModelProperty("五行概述")
    private String summary;
}
