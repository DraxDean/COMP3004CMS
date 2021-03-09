package com.COMP3004CMS.cms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Controller {

    @GetMapping("")
    public String homePage(){
        return "home";
    }

    @GetMapping("/login")
    public String hello(){
        return "login";
    }
}
