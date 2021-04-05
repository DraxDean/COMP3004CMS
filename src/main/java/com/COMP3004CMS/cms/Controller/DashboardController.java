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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
        }
        Deliverable deliverable = deliverableService.findDeliverableByDeliverableid(deliverableid);
        model.addAttribute("deliverable", deliverable);
        model.addAttribute("actions", actions);
        return "dashboard/deliverablepage";
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
}
