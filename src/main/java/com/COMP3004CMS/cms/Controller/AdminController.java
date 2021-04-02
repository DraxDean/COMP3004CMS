package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Service.CourseService;
import com.COMP3004CMS.cms.Service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Year;
import java.util.List;

@Controller()
public class AdminController {
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;
    @Autowired
    private CourseService courseService;

    @GetMapping("/admin")
    public String getAdminHome() {
        return "admin/adminhomepage";
    }

    //listing users by ID
    @GetMapping("/admin/user")
    public String getApprove(Model model, @RequestParam("id") String id) {
        User user = userDetailServiceImp.findUserById(id);
        model.addAttribute("user", user);
        return "admin/userprofile";
    }
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
    public String deleteUser(@RequestParam("id") String id) {
        userDetailServiceImp.deleteById(id);
        return "redirect:/admin/user/all";
    }
    @GetMapping("/admin/user/deny")
    public String denyUser(@RequestParam("id") String id) {
        userDetailServiceImp.deleteById(id);
        return "redirect:/admin/user/request";
    }

    @GetMapping("/admin/user/approve")
    public String approveUser(@RequestParam("id") String id) {
        //System.out.println("Approving users...");
        userDetailServiceImp.approveUserById(id);
        return "redirect:/admin/user/request";
    }
    @GetMapping("/admin/user/add")
    public String adminAddUser() {
        return "admin/adduser";
    }

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
            user.setPassword("123456");
            userDetailServiceImp.saveUser(user);
            userDetailServiceImp.approveUserById(user.getId());

        }
        return "redirect:/admin/user/all";
    }

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

    @PostMapping("/admin/course/add")
    public String postAddCourse(Course course, BindingResult bindingResult,
                                @RequestParam("prof") String userid) {
        Course courseExists = courseService.findByCourseid(course.getCourseid());
        User professors = userDetailServiceImp.findUserByUserid(userid);
        if (courseExists!= null) {
            bindingResult
                    .rejectValue("courseid", "error.course",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
        } else {
            Course shortCourse = new Course(course.id, course.courseid, course.department,
                    course.coursecode,course.title, course.maxSeats, course.term, course.year);
            User shortUser = new User(professors.id, professors.userid, professors.firstname,
                    professors.lastname,professors.getRoles());
            course.setProfessor(shortUser);
            professors.addCourse(shortCourse);
            userDetailServiceImp.saveUser(professors);
            courseService.saveCourse(course);
        }
        return "redirect:/admin/course/all";
    }

    //admin delete a course
    @GetMapping("/admin/course/delete")
    public String postAddCourse(@RequestParam("courseid") String courseid) {
        courseService.deleteCourseByCourseid(courseid);
        return "redirect:/admin/course/all";
    }

    @GetMapping("/admin/course/edit")
    public String getEditCourse(Model model, @RequestParam("courseid") String courseid) {
        Course course = courseService.findByCourseid(courseid);
        List<User> professors = userDetailServiceImp.findAllByRoles("PROFESSOR");

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
                course.coursecode,course.title, course.maxSeats, course.term, course.year);
        professors.addCourse(shortCourse);
        userDetailServiceImp.saveUser(professors);
        courseService.saveCourse(course);
        return "redirect:/admin/course/all";
    }
}