package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.AbstractFactoryDeliverable.Deliverable;
import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Service.CourseService;
import com.COMP3004CMS.cms.Service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller()
public class RegisterController {
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;
    @Autowired
    private CourseService courseService;


    //seeing course detail info
    @GetMapping("/register/audit")
    public String getAudit(Model model, Authentication authentication) {
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        List<Course> courseList = user.getCourseList();
        model.addAttribute("user", user);
        model.addAttribute("courses", courseList);
        return "register/myaudit";
    }
    //listing all course
    @GetMapping("/register/search")
    public String getAllCourse(Model model, Authentication authentication) {
        List<Course> course = courseService.findAll();  //will change to findAllByTerm
        User temp = userDetailServiceImp.findByUsername(authentication.getName());
        User student = new User(temp.id, temp.userid, temp.firstname, temp.lastname, temp.getRoles());
        for(Course c: course){
            if(c.getStudents().contains(student)){
                c.setStatus("Drop");
                c.setAction("/drop");
            }else {
                c.setStatus("Register");
                c.setAction("/register");
            }
        }
        model.addAttribute("courses", course);
        return "register/courseall";
    }

    //seeing course detail info
    @GetMapping("/register/course")
    public String showCourseDetail(Model model, @RequestParam("courseid") String courseid) {
        Course course = courseService.findByCourseid(courseid);
        model.addAttribute("course", course);
        return "courseprofile";
    }


    @GetMapping("/register")
    public String registerStudent(@RequestParam("courseid") String courseid,
                                  Authentication authentication) {
        Course course = courseService.findByCourseid(courseid);

        User student = userDetailServiceImp.findByUsername(authentication.getName());
        User shortUser = new User(student.id, student.userid, student.firstname, student.lastname,
                student.getRoles());
        //should check for credit max
        Course shortCourse = new Course(course.id, course.courseid, course.department,
                course.coursecode, course.title, course.section, course.term, course.year);
        course.addStudent(shortUser);
        student.addCourse(shortCourse);
        courseService.saveCourse(course);
        userDetailServiceImp.update(student);
        return "redirect:/register/search";
    }

    @GetMapping("/drop")
    public String dropStudent(@RequestParam("courseid") String courseid,
                              Authentication authentication) {
        Course course = courseService.findByCourseid(courseid);
        User student = userDetailServiceImp.findByUsername(authentication.getName());
        User shortUser = new User(student.id, student.userid, student.firstname, student.lastname,
                student.getRoles());
        course.removeStudent(shortUser);
        Course shortCourse = new Course(course.id, course.courseid,course.department,
                course.coursecode, course.title, course.maxSeats);
        student.dropCourse(shortCourse);
        courseService.saveCourse(course);
        userDetailServiceImp.update(student);
        return "redirect:/register/search";
    }
}
