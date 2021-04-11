package com.COMP3004CMS.cms.report.professorReports;


import com.COMP3004CMS.cms.AbstractFactoryDeliverable.LongDeliverable;
import com.COMP3004CMS.cms.AbstractFactoryDeliverable.Quiz;
import com.COMP3004CMS.cms.Model.Course;

import java.util.HashMap;

public interface Visitor {
    HashMap<String, Double> visitCourse(Course course);

    HashMap<String, Double> visitLongDeliverable(LongDeliverable ld);
    HashMap<String, Double> visitQuiz(Quiz ld);
    //public HashMap<String, Double> visitShortDeliverable( ShortDeliverable sd);
}
