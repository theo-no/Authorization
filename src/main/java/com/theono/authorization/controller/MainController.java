package com.theono.authorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {

    @GetMapping
    public String indexPage(){ return "index page"; }


    @GetMapping("/admin")
    public String adminPage(){
        return "admin page";
    }

}
