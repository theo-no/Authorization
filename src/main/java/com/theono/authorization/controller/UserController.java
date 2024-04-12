package com.theono.authorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserController {
    @GetMapping("/user")
    @ResponseBody
    public String loginPage(){
        return "welcome user page";
    }
}
