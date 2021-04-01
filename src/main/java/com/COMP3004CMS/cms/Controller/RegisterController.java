package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Model.Student;
import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Service.CourseService;
import com.COMP3004CMS.cms.Service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller()
public class RegisterController {
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;
    @Autowired
    private CourseService courseService;


    //listing all course
    @GetMapping("/register/search")
    public String getAllCourse(Model model) {
        List<Course> course = courseService.findAll();  //will change to findAllByTerm
        model.addAttribute("courses", course);
        return "course_search";
    }

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
        if (course.getStudents()==null) {
            course.setStudents(new ArrayList<User>());
        }
        if (course.getWaitList()==null) {
            course.setWaitlist(new ArrayList<User>());
        }
        User student = userDetailServiceImp.findByUsername(authentication.getName());
        //should check for credit max
        course.addStudent(student);
        if (student.getCourseList()==null) {
            student.setCourseList(new ArrayList<Course>());
        }
        Course temp = new Course(course.id, course.courseid, course.department,
                course.coursecode,course.title, course.maxSeats);
        student.addCourse(temp);
        courseService.saveCourse(course);
        userDetailServiceImp.saveUser(student);
        return "redirect:/register/search";
    }
    @GetMapping("/drop")
    public String dropStudent(@RequestParam("courseid") String courseid,
                                   HttpServletRequest request) {
        Course course = courseService.findByCourseid(courseid);
        User student = userDetailServiceImp.findUserByUserid((String) request.getAttribute("userid"));
        course.removeStudent(student);
        Course shortCourse = new Course(course.id, course.courseid,course.department,course.coursecode, course.title, course.maxSeats);
        student.addCourse(shortCourse);
        return "redirect:/register/search";
    }
}
