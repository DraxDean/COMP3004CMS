package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/courses")
    public String showAllCourse(Model model) {
        List<Course> course = courseService.findAll();
        model.addAttribute("courses", course);
        return "course";
    }

    @GetMapping("/courses/addcourse")
    public String getAddCourse() {
        return "addcourse";
    }

    @PostMapping("/courses/addcourse")
    public String postAddCourse(Course course, BindingResult bindingResult) {
        Optional<Course> courseExists = courseService.findByCoursecode(course.getCoursecode());
        if (courseExists.isPresent()) {
            bindingResult
                    .rejectValue("id", "error.course",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            System.out.print("fail");
        } else {
            courseService.saveCourse(course);
            System.out.print("creating course");
        }
        return "addcourse";
    }
}
