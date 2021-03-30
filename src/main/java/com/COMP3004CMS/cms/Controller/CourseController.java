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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class CourseController {

    @Autowired
    CourseService courseService;

    // "/courses/courseid"
    @GetMapping("/courses/{courseid}")
    public String showCourse(Model model, @RequestParam("courseid") String courseid) {
        Course course = courseService.findByCourseid(courseid);
        model.addAttribute("course", course);
        return "courseprofile";
    }

    /**
     *  Send back public data instead of private course info
     * @param model
     * @return add course page
     */
    @GetMapping("/add/course")
    public String addCourse(Model model) {
        List<Course> courses = courseService.findAll();
        ArrayList<Course.CourseItem> courseList = new ArrayList<Course.CourseItem>();
        for (Course c: courses) {
            courseList.add(c.toPublic());
        }
        model.addAttribute("courseList", courseList);
        return "addcourse";
    }
    @PostMapping("/add/course")
    public String postAddCourse(Course course, BindingResult bindingResult) {
        System.out.println(course.getCoursecode());
        if (courseService.findByCourseid(course.getCourseid()) != null) {
            bindingResult
                    .rejectValue("courseid", "error.course",
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

    /**
     *  getting the add time page
     * @param model
     * @return page with addTime widget section
     */
    @GetMapping("/add/course/times")
    public String getAddCourse(Model model) {
        //get available timeslots for course
        List<Course> course = courseService.findAll();
        model.addAttribute("courses", course);
        model.addAttribute("time", null);
        return "addcourse";
    }

    @PostMapping("/add/course/time")
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





    @DeleteMapping("/delete/{courseCode}")
    public void deleteByCourseCode(@PathVariable("courseCode") String courseCode){
        courseService.deleteByClassCode(courseCode);
    }
    @DeleteMapping("/delete/all")
    public void deleteByCourseCode(Model model){
        List<Course> courses = courseService.findAll();
        for (Course c: courses) {
            courseService.deleteByClassCode(c.getCoursecode());
        }

    }

}
