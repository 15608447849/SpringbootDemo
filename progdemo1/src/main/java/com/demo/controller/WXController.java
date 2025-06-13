package com.demo.controller;

import com.demo.service.user.RoutineService;
import com.demo.service.wchat.WXMinProgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/wxapi", produces = "application/json;charset=UTF-8")
public class WXController {

    @Autowired
    private WXMinProgService wxService;

    @Autowired
    private RoutineService routineService;

    @PostMapping("/get-openid")
    public Object getOpenid(@RequestBody Map<String, String> map ){
        return wxService.getOpenid(map.get("code"));
    }

    @PostMapping("/check-registration")
    public boolean checkRegister(@RequestBody Map<String, String> map){
        return routineService.checkRegister( map.get("openid"));
    }

    @PostMapping("/register")
    public boolean register(@RequestBody Map<String, String> map){
        return routineService.register(
                map.get("openid"),
                map.get("email"),
                map.get("phone"),
                map.get("nickname"),
                map.get("imgurl")
        );
    }



}
