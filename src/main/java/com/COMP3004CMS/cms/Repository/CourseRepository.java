package com.COMP3004CMS.cms.Repository;

import com.COMP3004CMS.cms.Model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {
   Course findByCourseid(String courseid);

   @Override
   void deleteById(String s);
}
