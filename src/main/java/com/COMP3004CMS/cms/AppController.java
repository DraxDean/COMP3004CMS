package com.COMP3004CMS.cms;

import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Repository.UserRepository;
import com.COMP3004CMS.cms.Service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;

@Controller
//@RequestMapping
public class AppController {


    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("")
    public String homePage(){
        //userRepository.save(new User("ivoryzhang", "123456"));
        return "home";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
    @GetMapping("/signup")
    public String getSignup(){
        User user = new User();
        return "signup";
    }

    @PostMapping("/signup")
    public String getSignup(User user, BindingResult bindingResult){
        //User user = new User("ivoryzhang", encoder.encode("123456"));
        //userRepository.save(user);
        User userExists = userDetailServiceImp.findByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            return "signup";
        } else {
            userDetailServiceImp.saveUser(user);
            return "redirect:/login";

        }
    }


    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }

}
