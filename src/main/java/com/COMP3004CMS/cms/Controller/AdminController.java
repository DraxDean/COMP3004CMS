package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Service.CourseService;
import com.COMP3004CMS.cms.Service.UserDetailServiceImp;
import com.COMP3004CMS.cms.Service.LogService;
import com.COMP3004CMS.cms.Visitor.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Year;
import java.util.List;

@Controller()
public class AdminController {
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;
    @Autowired
    private CourseService courseService;
    @Autowired
    private LogService logService;

    @GetMapping("/admin")
    public String getAdminHome(Model model) {
        User user = userDetailServiceImp.findByUsername("admin");
        model.addAttribute("announcements", user.getAnnouncements());
        LogManager lm = new LogManager(logService.getLogs());
        model.addAttribute("logs", lm.getLogs());
        return "admin/adminhomepage";
    }

    //listing users by ID
    @GetMapping("/admin/user")
    public String getApprove(Model model, @RequestParam("id") String id) {
        User user = userDetailServiceImp.findUserById(id);
        model.addAttribute("user", user);
        return "admin/userprofile";
    }

    //listing all users
    @GetMapping("/admin/user/all")
    public String getApprove(Model model) {
        List<User> student = userDetailServiceImp.findAllByRoles("STUDENT");
        List<User> professor = userDetailServiceImp.findAllByRoles("PROFESSOR");
        model.addAttribute("student", student);
        model.addAttribute("professor", professor);
        return "admin/userall";
    }

    @GetMapping("/admin/user/request")
    public String getAllUsers(Model model) {
        List<User> pendingStudent = userDetailServiceImp.findAllByRoles("STUDENT_PENDING");
        List<User> pendingProf = userDetailServiceImp.findAllByRoles("PROFESSOR_PENDING");
        model.addAttribute("pending_student", pendingStudent);
        model.addAttribute("pending_prof", pendingProf);
        return "admin/approveuser";
    }

    @GetMapping("/admin/user/delete")
    public String deleteUser(@RequestParam("id") String id,
                             RedirectAttributes redirectAttributes) {
        Boolean success = userDetailServiceImp.deleteById(id);
        if(!success){
            String err = "Can't delete user when they are in courses";
            redirectAttributes.addFlashAttribute("message", err);
        }
        return "redirect:/admin/user/all";
    }

    //deny account application
    @GetMapping("/admin/user/deny")
    public String denyUser(@RequestParam("id") String id) {
        userDetailServiceImp.deleteById(id);
        return "redirect:/admin/user/request";
    }

    //approve account application
    @GetMapping("/admin/user/approve")
    public String approveUser(@RequestParam("id") String id) {
        //System.out.println("Approving users...");
        userDetailServiceImp.approveUserById(id);
        return "redirect:/admin/user/request";
    }

    //add user by admin
    @GetMapping("/admin/user/add")
    public String adminAddUser() {
        return "admin/adduser";
    }

    //post add user by admin
    @PostMapping("/admin/user/add")
    public String adminAddUserPost(User user, BindingResult bindingResult) {
        User checkUsername = userDetailServiceImp.findByUsername(user.getUsername());
        User checkStuID = userDetailServiceImp.findUserByUserid(user.getUserid());
        if (checkUsername != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (checkStuID != null) {
            bindingResult
                    .rejectValue("userid", "error.user",
                            "There is already a user registered with the student number provided");
        }
        if (bindingResult.hasErrors()) {

        } else {
            user.setPassword("123456");     //initial password
            userDetailServiceImp.saveUser(user);
            userDetailServiceImp.approveUserById(user.getId());

        }
        return "redirect:/admin/user/all";
    }

    //see each courses
    @GetMapping("/admin/course")
    public String showAllCourse(Model model, @RequestParam("courseid") String courseid) {
        Course course = courseService.findByCourseid(courseid);
        model.addAttribute("course", course);
        return "courseprofile";
    }

    //see all courses
    @GetMapping("/admin/course/all")
    public String showAllCourse(Model model) {
        List<Course> course = courseService.findAll();
        model.addAttribute("courses", course);
        return "admin/courseall";
    }

    //admin add course
    @GetMapping("/admin/course/add")
    public String getAddCourse(Model model) {
        List<User> professors = userDetailServiceImp.findAllByRoles("PROFESSOR");
        model.addAttribute("profs", professors);
        return "admin/addcourse";
    }
    //post admin add course
    @PostMapping("/admin/course/add")
    public String postAddCourse(Course course, BindingResult bindingResult,
                                @RequestParam("prof") String userid) {
        course.setCourseid();
        Course courseExists = courseService.findCourseByDepartmentAndCoursecodeAndSection(course.getDepartment(),
                course.getCoursecode(), course.getSection());
        User professors = userDetailServiceImp.findUserByUserid(userid);
        if (courseExists!= null) {
            bindingResult
                    .rejectValue("courseid", "error.course",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
        } else {
            Course shortCourse = new Course(course.id, course.courseid, course.department,
                    course.coursecode, course.title, course.section, course.term, course.year);
            User shortUser = new User(professors.id, professors.userid, professors.firstname,
                    professors.lastname,professors.getRoles());
            course.setProfessor(shortUser);
            professors.addCourse(shortCourse);
            userDetailServiceImp.update(professors);
            courseService.saveCourse(course);
        }
        return "redirect:/admin/course/all";
    }

    //admin delete a course
    @GetMapping("/admin/course/delete")
    public String postAddCourse(@RequestParam("courseid") String courseid,
                                RedirectAttributes redirectAttrs) {
        //can't delete course if there are students in
        Course course = courseService.findByCourseid(courseid);
        Boolean success = courseService.deleteCourseByCourseid(courseid);
        if(!success){
            String err = "Can't delete course when there are students in this course";
            redirectAttrs.addFlashAttribute("message", err);
        }else {
            //if delete, delete prof's course list as well
            String userid = course.getProfessor().getUserid();
            User professor = userDetailServiceImp.findUserByUserid(userid);
            professor.dropCourse(course);
        }
        return "redirect:/admin/course/all";
    }

    @GetMapping("/admin/course/edit")
    public String getEditCourse(Model model, @RequestParam("courseid") String courseid) {
        Course course = courseService.findByCourseid(courseid);
        List<User> professors = userDetailServiceImp.findAllByRoles("PROFESSOR");
        User temp = course.getProfessor();
        User professor = new User();
        if (temp!=null){
            professor = userDetailServiceImp.findUserByUserid(temp.getUserid());
            if (professor!=null){
                professor.dropCourse(course);
            }
        }
        //              //remove course
        userDetailServiceImp.update(professor);
        model.addAttribute("profs", professors);
        model.addAttribute("course", course);
        return "admin/editcourse";
    }

    @PostMapping("/admin/course/edit")
    public String postEditCourse(@RequestParam("department") String department, @RequestParam("coursecode") String coursecode,
                                 @RequestParam("section") String section, @RequestParam("maxSeats") int maxSeats,
                                 @RequestParam("description") String description,
                                 @RequestParam("term") String term, @RequestParam("year") String year,
                                 @RequestParam("courseid") String courseid, @RequestParam("prof") String userid){
        Course course = courseService.findByCourseid(courseid);
        User professors = userDetailServiceImp.findUserByUserid(userid);
        User shortUser = new User(professors.id, professors.userid, professors.firstname,
                professors.lastname,professors.getRoles());
        course.setDepartment(department);
        course.setCoursecode(coursecode);
        course.setSection(section);
        course.setMaxSeats(maxSeats);
        course.setDescription(description);
        course.setTerm(term);
        course.setYear(year);
        course.setProfessor(shortUser);
        Course shortCourse = new Course(course.id, course.courseid, course.department,
                course.coursecode,course.title, course.section, course.term, course.year);
        professors.addCourse(shortCourse);
        userDetailServiceImp.update(professors);
        courseService.saveCourse(course);
        return "redirect:/admin/course/all";
    }
}