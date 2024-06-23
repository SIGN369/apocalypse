package com.mission.apocalypse.controller;


import com.mission.apocalypse.alogrithm.PanelAlgUtil;
import com.mission.apocalypse.controller.request.UserQueryInfo;
import com.mission.apocalypse.controller.response.LightFateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/app")
@Api("生辰八字")
public class ChangeController {


    @PostMapping("/cal")
    @ApiOperation("八字测算")
    public ResponseEntity<LightFateResponse> basic(@RequestBody UserQueryInfo queryInfo) {

        return ResponseEntity.ok(PanelAlgUtil.cal(queryInfo));
    }

}
