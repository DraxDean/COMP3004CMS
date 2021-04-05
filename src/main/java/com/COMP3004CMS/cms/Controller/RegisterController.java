package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Model.Deliverable;
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


    //listing all course
    @GetMapping("/register/search")
    public String getAllCourse(Model model, Authentication authentication) {
        List<Course> course = courseService.findAll();  //will change to findAllByTerm
        User student = userDetailServiceImp.findByUsername(authentication.getName());
        for(Course c: course){
            if (c.getStudents() == null){
                c.setStudents(new ArrayList<>());
            }
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
                course.coursecode,course.title, course.maxSeats, course.term, course.year);
        //
        ArrayList<Deliverable> deliverables = course.getDeliverables();
        if(deliverables != null){
            for(Deliverable deliverable: deliverables){
                deliverable.addStudent(shortUser);
            }
        }
        course.setDeliverables(deliverables);
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
