package com.COMP3004CMS.cms.Service;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Repository.CourseRepository;
import com.COMP3004CMS.cms.Visitor.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService{

    @Autowired
    private CourseRepository courseRepository;

    public Course findByCourseid(String courseid){
        return courseRepository.findByCourseid(courseid);
    }

    public Course findCourseByDepartmentAndCoursecodeAndSection(String department, String coursecode, String section){
        return courseRepository.findCourseByDepartmentAndCoursecodeAndSection(department, coursecode, section);
    }

    public void saveCourse(Course course) {
        course.accept(new LogManager());
        courseRepository.save(course);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public boolean deleteCourseByCourseid(String id){
        Course course = courseRepository.findByCourseid(id);

        if (course.getStudents()==null) {
            courseRepository.deleteCourseByCourseid(id);
            return true;
        }else {
            if (!course.getStudents().isEmpty()){
                return false;
            }else {
                courseRepository.deleteCourseByCourseid(id);
                return true;
            }
        }
    }
}
