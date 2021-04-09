package com.COMP3004CMS.cms.Repository;

import com.COMP3004CMS.cms.Model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {
   Course findByCourseid(String courseid);
   Course findCourseByDepartmentAndCoursecodeAndSection(String department,
                                                        String coursecode,
                                                        String section);
   void deleteCourseByCourseid(String courseid);
   @Override
   void deleteById(String s);
}
