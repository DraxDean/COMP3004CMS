package com.COMP3004CMS.cms.Service;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService{

    /* What does all this do? */

    @Autowired
    private CourseRepository courseRepository;

    public Course findByCourseid(String courseid){
        return courseRepository.findByCourseid(courseid);
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public void deleteById(String id){
        courseRepository.deleteById(id);
    }
    public boolean deleteCourseByCourseid(String id){
        Course course = courseRepository.findByCourseid(id);
        if (course.getStudents()!=null){
            return false;
        }else {
            courseRepository.deleteCourseByCourseid(id);
            return true;
        }
    }

    public boolean deleteCourseByCourseMongoID(String id){
            courseRepository.deleteCourseByCourseid(id);
            return true;
    }
}
