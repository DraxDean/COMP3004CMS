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

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        List<Course> courseList = user.getCourseList();
        ArrayList<String> announcement = user.getAnnouncements();
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

        Action action = new Action();
        if(user.getRoles().equals("PROFESSOR")){
            action.setAction("/dashboard/deliverable/add?courseid="+courseid);
            action.setButton("Add Deliverable");
        }
        model.addAttribute("course", course);
        model.addAttribute("action", action);
        model.addAttribute("courseid", courseid);
        return "dashboard/coursepage";
    }

    //culearn assignment page
    @GetMapping("/dashboard/deliverable")
    public String showDeliverable(Model model, @RequestParam("id") String deliverableid,
                                  Authentication authentication) {
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        Deliverable deliverable = deliverableService.findDeliverableByDeliverableid(deliverableid);
        ArrayList<User> students = deliverable.getStudents();
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

            model.addAttribute("students", students);
            model.addAttribute("deliverable", deliverable);
            model.addAttribute("actions", actions);
            return "dashboard/deliverablepage";

        }else if(user.getRoles().equals("STUDENT")){
            Action submiutAction = new Action();
            submiutAction.setAction("/dashboard/deliverable/submit?id="+deliverableid);
            submiutAction.setButton("Submit this deliverable");

            User submission = deliverable.findStudent(user);
            model.addAttribute("submission", submission);
            model.addAttribute("deliverable", deliverable);
            model.addAttribute("actions", actions);
            return "dashboard/deliverable_student";
        }else{
            return "redirect://default";
        }
    }

    //prof add deliverable
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
    public String postDeliverable(Model model, @RequestParam("courseid") String courseid,
                                  Deliverable deliverable, BindingResult bindingResult) {
        Course course = courseService.findByCourseid(courseid);
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

    //prof add deliverable
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

    //post add deliverable
    @PostMapping("/dashboard/deliverable/edit")
    public String postEditDeliverable(Model model, @RequestParam("id") String deliverableid,
                                      @RequestParam("title") String title,
                                      @RequestParam("start") String start,
                                      @RequestParam("deadline") String deadline,
                                      @RequestParam("requirements") String requirements) {
        Deliverable deliverable = deliverableService.findDeliverableByDeliverableid(deliverableid);
        Course course = courseService.findByCourseid(deliverable.courseid);
        course.deleteDeliverable(deliverable);
        //setter
        deliverable.setTitle(title);
        deliverable.setStart(start);
        deliverable.setDeadline(deadline);
        deliverable.setRequirements(requirements);
        //save
        course.addDeliverable(deliverable);
        courseService.saveCourse(course);
        deliverableService.save(deliverable);
        model.addAttribute("course", course);
        return "redirect:/dashboard/course?courseid="+deliverable.courseid;
    }

    //post add deliverable
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

    //post add deliverable
    @GetMapping("/dashboard/deliverable/submissions")
    public String postDeliverable(Model model, @RequestParam("id") String deliverableid,
                                  Authentication authentication) {
        User user = userDetailServiceImp.findByUsername(authentication.getName());
        if(!user.getRoles().equals("PROFESSOR")){
            return "redirect:/dashboard";
        }
        Deliverable deliverable = deliverableService.findDeliverableByDeliverableid(deliverableid);
        ArrayList<User> students_submissions = deliverable.getStudents();
        model.addAttribute("deliverable", deliverable);
        model.addAttribute("students_submissions", students_submissions);
        return "/dashboard/allsubmission";
    }

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
        return "redirect:/dashboard/deliverable/submissions?id="+deliverableid;
    }

}
