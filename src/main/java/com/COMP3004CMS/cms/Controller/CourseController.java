package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Model.Time;
import com.COMP3004CMS.cms.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/add")
    public String showAllCourse(Model model) {
        List<Course> course = courseService.findAll();
        model.addAttribute("courses", course);
        return "addcourse";
    }

    /**
     *  getting the add time page
     * @param model
     * @return page with addTime widget section
     */
    @GetMapping("/add-times")
    public String getAddCourse(Model model) {
        //get available timeslots for course
        List<Course> course = courseService.findAll();
        model.addAttribute("courses", course);
        model.addAttribute("time", null);
        return "addcourse";
    }

    @PostMapping("/add-time")
    public String createTimeObject(@Validated @ModelAttribute("time") Time time,
                                   BindingResult bindingResult, Model model){
        //If the Time object is not valid
        if (bindingResult.hasErrors()) {
          return "addCourse";
      }
        //return page with time attribute
        model.addAttribute("time", time);
        return "addCourse";

    }

    @GetMapping("/add/addcourse")
    public String getAddCourse() {
        return "addcourse";
    }

    @PostMapping("/add/addcourse")
    public String postAddCourse(Course course, BindingResult bindingResult) {
        Optional<Course> courseExists = courseService.findByCourseid(course.getCourseid());
        if (courseExists.isPresent()) {
            bindingResult
                    .rejectValue("courseid", "error.course",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            //System.out.print("fail");
        } else {
            courseService.saveCourse(course);
            //System.out.print("creating course");
        }
        return "addcourse";
    }
}
