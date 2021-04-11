package com.COMP3004CMS.cms.report.professorReprts;



import com.COMP3004CMS.cms.AbstractFactoryDeliverable.Deliverable;
import com.COMP3004CMS.cms.AbstractFactoryDeliverable.LongDeliverable;
import com.COMP3004CMS.cms.AbstractFactoryDeliverable.Quiz;
import com.COMP3004CMS.cms.Model.Course;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrentGradeVisitor implements Visitor{

    /**
     * Get the average grade for each student across all delierable grades in the course
     * @param course course containing arrayList of deliverables
     * @return hashmap of every student num as key and associated average grade values
     */
    @Override
    public HashMap<String, Double> visitCourse(Course course) {
        HashMap<String, Double> totalStudentGrades =  new HashMap<>();
        double deliverableNum = course.getDeliverables().size();
        for (Deliverable d: course.getDeliverables()) {
            HashMap<String, Double> grade = d.accept(this);
            //go through all student grades and add to total grades
            grade.forEach((k,v) -> {
                if (!totalStudentGrades.containsKey(k)){
                    totalStudentGrades.put(k,(v/deliverableNum));
                } else {
                    totalStudentGrades.put(k, totalStudentGrades.get(k)+(v/deliverableNum));
                }
            });
        }

        //go into inputs af
        return totalStudentGrades;
    }

    @Override
    public HashMap<String, Double> visitLongDeliverable(LongDeliverable ld) {
        HashMap<String, Double> gradesByStu = new HashMap<>();
        ld.getSubmissions().forEach((k,v)-> {
            //grab the last submission that the student did
            gradesByStu.put(k, v.getLast().getGrade());
        });
        return gradesByStu;
    }

    @Override
    public HashMap<String, Double> visitQuiz(Quiz ld) {
        return null;
    }



}
