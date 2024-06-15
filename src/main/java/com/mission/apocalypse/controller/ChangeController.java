package com.mission.apocalypse.controller;


import com.mission.apocalypse.controller.request.UserQueryInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ChangeController {


    @PostMapping
    public String basic(@RequestBody UserQueryInfo queryInfo) {

        //
        return "";
    }

}
