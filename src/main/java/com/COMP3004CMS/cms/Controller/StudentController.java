package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    CourseService courseService;

    /**
     *  For sending register course page, right now only sending json of courses
     * @param model
     * @return json of formatted courses
     */
    @GetMapping("/register")
    public ArrayList<Course.CourseItem> registerCourse(Model model) {
        //get all courses in db
        List<Course> courses = courseService.findAll();
        ArrayList<Course.CourseItem> courseList = new ArrayList<Course.CourseItem>();
        for (Course c: courses) {
            courseList.add(c.toPublic());
        }
        model.addAttribute("courseList", courseList);
        return courseList;
    }
    //ask for a list of all subjects
    //ask for a list of all years
    //pick one course
    //apply to register in course

}
