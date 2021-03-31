package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Service.CourseService;
import com.COMP3004CMS.cms.Service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller()
public class AdminController {
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;
    @Autowired
    private CourseService courseService;

    @GetMapping("/admin")
    public String getAdminHome() {
        return "adminhomepage";
    }

    //listing users by ID
    @GetMapping("/admin/user")
    public String getApprove(Model model, @RequestParam("id") String id) {
        User user = userDetailServiceImp.findUserById(id);
        model.addAttribute("user", user);
        return "userprofile";
    }
    @GetMapping("/admin/user/all")
    public String getApprove(Model model) {
        List<User> student = userDetailServiceImp.findAllByRoles("STUDENT");
        List<User> professor = userDetailServiceImp.findAllByRoles("PROFESSOR");
        model.addAttribute("student", student);
        model.addAttribute("professor", professor);
        return "userall";
    }

    @GetMapping("/admin/user/request")
    public String getAllUsers(Model model) {
        List<User> pendingStudent = userDetailServiceImp.findAllByRoles("STUDENT_PENDING");
        List<User> pendingProf = userDetailServiceImp.findAllByRoles("PROFESSOR_PENDING");
        model.addAttribute("pending_student", pendingStudent);
        model.addAttribute("pending_prof", pendingProf);
        return "approveuser";
    }

    @GetMapping("/admin/user/delete")
    public String deleteUser(@RequestParam("id") String id) {
        userDetailServiceImp.deleteById(id);
        return "redirect:/admin/user/all";
    }
    @GetMapping("/admin/user/deny")
    public String denyUser(@RequestParam("id") String id) {
        userDetailServiceImp.deleteById(id);
        return "redirect:/admin/user/request";
    }

    @GetMapping("/admin/user/approve")
    public String approveUser(@RequestParam("id") String id) {
        //System.out.println("Approving users...");
        userDetailServiceImp.approveUserById(id);
        return "redirect:/admin/user/request";
    }
    @GetMapping("/admin/user/add")
    public String adminAddUser() {
        return "adduser";
    }

    @PostMapping("/admin/user/add")
    public String adminAddUserPost(User user, BindingResult bindingResult) {
        User checkUsername = userDetailServiceImp.findByUsername(user.getUsername());
        User checkStuID = userDetailServiceImp.findUserByUserid(user.getUserid());
        if (checkUsername != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (checkStuID != null) {
            bindingResult
                    .rejectValue("userid", "error.user",
                            "There is already a user registered with the student number provided");
        }
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/user/add";
        } else {
            user.setPassword("123456");
            userDetailServiceImp.saveUser(user);
            userDetailServiceImp.approveUserById(user.getId());
            return "redirect:/admin/user/all";
        }
        //return "adduser";
    }

    @GetMapping("/admin/course")
    public String showAllCourse(Model model, @RequestParam("courseid") String courseid) {
        Course course = courseService.findByCourseid(courseid);
        model.addAttribute("course", course);
        return "courseprofile";
    }

    //see all courses
    @GetMapping("/admin/course/all")
    public String showAllCourse(Model model) {
        List<Course> course = courseService.findAll();
        model.addAttribute("courses", course);
        return "courseall";
    }
    @GetMapping("/admin/course/add")
    public String getAddCourse() {
        return "addcourse";
    }
    @PostMapping("/admin/courses/add")
    public String postAddCourse(Course course, BindingResult bindingResult) {
        Course courseExists = courseService.findByCourseid(course.getCourseid());
        if (courseExists!= null) {
            bindingResult
                    .rejectValue("courseid", "error.course",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
        } else {
            courseService.saveCourse(course);
        }
        return "addcourse";
    }

    @PostMapping("/admin/courses/delete")
    public String postAddCourse(@RequestParam("courseid") String courseid) {
        courseService.deleteById(courseid);
        return "redirect:/admin/course/all";
    }
}