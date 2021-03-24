package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
//@RequestMapping
public class AppController {
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("")
    public String homePage() {
        //userRepository.save(new User("ivoryzhang", "123456"));
        return "home";
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
            return "redirect:/login";
        }
    }


    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/user_approval")
    public String getApprove(Model model) {
        List<User> pendingStudent = userDetailServiceImp.findAllByRoles("STUDENT_PENDING");
        List<User> pendingProf = userDetailServiceImp.findAllByRoles("PROFESSOR_PENDING");
        model.addAttribute("pending_student", pendingStudent);
        model.addAttribute("pending_prof", pendingProf);
        return "admin";
    }

    @GetMapping("/error")
    public String error() {return "test";}

}
