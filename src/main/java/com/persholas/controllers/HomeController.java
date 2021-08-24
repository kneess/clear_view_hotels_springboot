package com.persholas.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("clearview")
@Slf4j
public class HomeController {

    @GetMapping({"/","/home"})
    public String goHome()
    {
        log.warn("executing: HomePage");
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}