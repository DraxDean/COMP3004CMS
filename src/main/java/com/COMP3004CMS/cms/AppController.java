package com.COMP3004CMS.cms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

@Controller
//@RequestMapping
public class AppController {

    @GetMapping("")
    public String homePage(){
        return "login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }

   /* @GetMapping("/error")
    public String error() {return "test";}*/

}
