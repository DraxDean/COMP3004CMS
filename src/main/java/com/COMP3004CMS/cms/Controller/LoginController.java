package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping
public class LoginController {
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String homePage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignup() {
        //com.COMP3004CMS.cms.Model.User user = new com.COMP3004CMS.cms.Model.User();
        return "signup";
    }

    @PostMapping("/signup")
    public String getSignup(User user, BindingResult bindingResult) {
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
            User admin = userDetailServiceImp.findByUsername("admin");
            admin.addAnnouncements("A new user creation: (" + user.userid + ") has been requested.");
            userDetailServiceImp.update(admin);
            return "redirect:/login";
        }
    }


    @GetMapping("/default")
    public String getDefault(HttpServletRequest request) {
        String role = request.getUserPrincipal().getName();
        if (role.equals("admin")) {
            return "redirect:/admin/";
        }
        return "redirect:/dashboard";
    }


    @GetMapping("/error")
    public String error() {return "test";}

    @GetMapping("/forgetpassword")
    public String forgetpassword(){
        return "forgetpassword";
    }
}
