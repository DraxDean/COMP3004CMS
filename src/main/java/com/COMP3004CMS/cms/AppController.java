package com.COMP3004CMS.cms;

import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

@Controller
//@RequestMapping
public class AppController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String homePage(){
        //userRepository.save(new User("ivoryzhang", "123456"));
        return "home";
    }

    @GetMapping("/login")
    public String hello(){
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }

}
