package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.Model.Action;
import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Model.Deliverable;
import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Service.CourseService;
import com.COMP3004CMS.cms.Service.DeliverableService;
import com.COMP3004CMS.cms.Service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;
    @Autowired
    private CourseService courseService;
    @Autowired
    private DeliverableService deliverableService;

    //get dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        List<Course> courseList = user.getCourseList();
        ArrayList<String> announcement = user.getAnnouncements();
        model.addAttribute("user", user);
        model.addAttribute("announcements", announcement);
        model.addAttribute("courses", courseList);
        return "dashboard/dashboard";
    }

    //culearn course page
    @GetMapping("/dashboard/course")
    public String showCourse(Model model, @RequestParam("courseid") String courseid,
                             Authentication authentication) {
        Course course = courseService.findByCourseid(courseid);
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        if(user.getRoles().equals("PROFESSOR")){
            Action action = new Action();
            action.setAction("/dashboard/deliverable/add?courseid="+courseid);
            action.setButton("Add Deliverable");
            model.addAttribute("action", action);
        }
        if(user.getRoles().equals("STUDENT")){
            User student = course.getStudentsByUid(user.getUserid());
            ArrayList<String> announcement = student.getAnnouncements();
            model.addAttribute("announcements", announcement);
        }
        model.addAttribute("course", course);
        model.addAttribute("courseid", courseid);
        return "dashboard/coursepage";
    }

    //culearn assignment page
    @GetMapping("/dashboard/deliverable")
    public String showDeliverable(Model model, @RequestParam("id") String deliverableid,
                                  Authentication authentication) {
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        Deliverable deliverable = deliverableService.findDeliverableByDeliverableid(deliverableid);
        ArrayList<Action> actions = new ArrayList<>();
        if(user.getRoles().equals("PROFESSOR")){
            Action editAction = new Action();
            editAction.setAction("/dashboard/deliverable/edit?id="+deliverableid);
            editAction.setButton("Edit Deliverable");
            Action deleteAction = new Action();
            deleteAction.setAction("/dashboard/deliverable/delete?id="+deliverableid);
            deleteAction.setButton("Delete Deliverable");
            actions.add(editAction);
            actions.add(deleteAction);

            ArrayList<User> students = deliverable.getStudents();
            model.addAttribute("students_submissions", students);
        } else if(user.getRoles().equals("STUDENT")){
            Action submiutAction = new Action();
            submiutAction.setAction("/dashboard/deliverable/submit?id="+deliverableid);
            submiutAction.setButton("Submit this deliverable");
            User submission = deliverable.findStudent(user);
            model.addAttribute("submission", submission);
        } else{
            return "redirect://default";
        }
        model.addAttribute("deliverable", deliverable);
        model.addAttribute("actions", actions);
        return "dashboard/deliverablepage";
    }

    //Get prof add deliverable
    @GetMapping("/dashboard/deliverable/add")
    public String addDeliverable(Model model, @RequestParam("courseid") String courseid,
                                 Authentication authentication) {
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        if(!user.getRoles().equals("PROFESSOR")){
            return "redirect:/dashboard";
        }
        model.addAttribute("courseid", courseid);
        return "dashboard/adddeliverable";
    }

    //post add deliverable
    @PostMapping("/dashboard/deliverable/add")
    public String postDeliverable(@RequestParam("courseid") String courseid,
                                  @RequestParam("type") String type,
                                  Deliverable deliverable, BindingResult bindingResult, Model model) {
        Course course = courseService.findByCourseid(courseid);
        //Deliverable deliverable = DeliverableFactory.createByType(type, d);
        deliverable.setDeliverableid();
        deliverable.setStudents(course.getStudents());
        deliverable.initalSubmission();
        course.addDeliverable(deliverable);
        courseService.saveCourse(course);
        deliverableService.save(deliverable);
        model.addAttribute("course", course);
        return "redirect:/dashboard/course?courseid="+courseid;
    }

    //deliverable delete
    @GetMapping("/dashboard/deliverable/delete")
    public String deleteDeliverable(Model model, @RequestParam("id") String deliverableid) {
        Deliverable deliverable = deliverableService.findDeliverableByDeliverableid(deliverableid);
        Course course = courseService.findByCourseid(deliverable.courseid);
        course.deleteDeliverable(deliverable);
        courseService.saveCourse(course);
        deliverableService.deleteDeliverableByDeliverableid(deliverable.getDeliverableid());
        model.addAttribute("course", course);
        return "redirect:/dashboard/course?courseid="+deliverable.courseid;
    }

    //get mapping prof edit deliverable
    @GetMapping("/dashboard/deliverable/edit")
    public String editDeliverable(Model model, @RequestParam("id") String deliverableid,
                                 Authentication authentication) {
        Deliverable deliverable = deliverableService.findDeliverableByDeliverableid(deliverableid);
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        if(!user.getRoles().equals("PROFESSOR")){
            return "redirect:/dashboard";
        }
        model.addAttribute("deliverable", deliverable);
        return "dashboard/editdeliverable";
    }

    //post edit deliverable
    @PostMapping("/dashboard/deliverable/edit")
    public String postEditDeliverable(Model model, @RequestParam("id") String deliverableid,
                                      @RequestParam("title") String title,
                                      @RequestParam("start") String start,
                                      @RequestParam("deadline") String deadline,
                                      @RequestParam("requirements") String requirements,
                                      @RequestParam("grade") String grade) {
        Deliverable deliverable = deliverableService.findDeliverableByDeliverableid(deliverableid);
        Course course = courseService.findByCourseid(deliverable.courseid);
        course.deleteDeliverable(deliverable);
        //setter
        deliverable.setTitle(title);
        deliverable.setStart(start);
        deliverable.setDeadline(deadline);
        deliverable.setRequirements(requirements);
        deliverable.setGrade(grade);        //
        //save
        course.addDeliverable(deliverable);
        courseService.saveCourse(course);
        deliverableService.save(deliverable);
        model.addAttribute("course", course);
        return "redirect:/dashboard/course?courseid="+deliverable.courseid;
    }

    //post student answer deliverable
    @PostMapping("/dashboard/deliverable/submit")
    public String postDeliverable(Model model, @RequestParam("id") String deliverableid,
                                  @RequestParam("submission") String submission,
                                  Authentication authentication) {
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        Deliverable deliverable = deliverableService.findDeliverableByDeliverableid(deliverableid);
        User stuSubmission = deliverable.findStudent(user);
        stuSubmission.setSubmission(submission);
        deliverable.undateSubmissionByStudent(stuSubmission);

        deliverableService.save(deliverable);
        model.addAttribute("deliverable", deliverable);
        return "redirect:/dashboard/deliverable?id="+deliverable.getDeliverableid();
    }

    //post prof giving marks
    @PostMapping("/dashboard/deliverable/grade/{id}/user/{userid}")
    public String postDeliverableGrade(Authentication authentication, RedirectAttributes redirectAttributes,
                                       @PathVariable("id") String deliverableid,
                                       @PathVariable("userid") String userid,
                                       @RequestParam("grade") int grade) {
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        User student =  userDetailServiceImp.findUserByUserid(userid);
        if(!user.getRoles().equals("PROFESSOR")){
            return "redirect:/dashboard";
        }
        Deliverable deliverable = deliverableService.findDeliverableByDeliverableid(deliverableid);
        deliverable.undateGradeByStudent(student, grade);

        deliverableService.save(deliverable);
        return "redirect:/dashboard/deliverable?id="+deliverableid;
    }

    @GetMapping("/dashboard/course/grade")
    public String getCourseGrade(Authentication authentication, Model model,
                                  @RequestParam("id") String courseid) {
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        if(!user.getRoles().equals("PROFESSOR")){
            return "redirect:/dashboard";
        }
        Course course = courseService.findByCourseid(courseid);
        model.addAttribute("students", course.getStudents());
        model.addAttribute("course", course);
        return "dashboard/finalgrade";
    }

    @PostMapping("/dashboard/course/grade/{id}/{userid}")
    public String postCourseGrade(Authentication authentication, RedirectAttributes redirectAttributes,
                                       @PathVariable("id") String courseid,
                                       @PathVariable("userid") String userid,
                                       @RequestParam("grade") int grade) {
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        User student =  userDetailServiceImp.findUserByUserid(userid);
        if(!user.getRoles().equals("PROFESSOR")){
            return "redirect:/dashboard";
        }
        Course course = courseService.findByCourseid(courseid);
        course.undateGradeByStudent(student, grade);
        student.undateGradeByCourse(course, grade);
        userDetailServiceImp.update(student);
        courseService.saveCourse(course);
        return "redirect:/dashboard/course/grade?id="+courseid;
    }

}
