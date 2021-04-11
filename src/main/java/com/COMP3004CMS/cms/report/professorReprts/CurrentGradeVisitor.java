package com.COMP3004CMS.cms.report.professorReprts;



import com.COMP3004CMS.cms.FactoryMethodDeliverable.LongDeliverable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class CurrentGradeVisitor implements Visitor{
/*
    //sorted map of grade ranges
    //key = string of grade range
    //integer = number of entries in that range
    public TreeMap<String, Integer> export(ArrayList<Deliverable> args){
        TreeMap<String, Integer> gradesRange = new TreeMap<>();
        GradeConverter converter = new GradeConverter();
        //average grade for student
        //counter for amount of deliverables
        int counter = 0;
        for (Deliverable d: args) {
           counter++;
            //calculate the average grade for each student
            //go through each of the submissions
            //HashMap<String, Double> currentStudentGrades = d.accept(this);
            //currentStudentGrades.forEach((k, v) -> {
                //String gradeRange  = converter.convertToNumberRange(v);
                /*if(gradeRange)
              //      }



            //get last submission of each of them
            //check if convert is already in range


        //});

    }
        return gradesRange;
    }
    */


    @Override
    public HashMap<String, Double> visitLongDeliverable(LongDeliverable ld) {
        HashMap<String, Double> gradesByStu = new HashMap<>();
        ld.getSubmissions().forEach((k,v)-> {
            //grab the last submission that the student did
            gradesByStu.put(k, v.getLast().getGrade());
        });
        return gradesByStu;
    }

    /*
    public HashMap<String, Double> visitShortDeliverable(ShortDeliverable sd){
        return  HashMap<String, Double> totalStudentGrades = new HashMap<String, Double>();
    }

     */
/*
    public TreeMap<String, Integer> visitCourse(Course course){
        TreeMap<String, Integer> gradesRange = new TreeMap<>();
        GradeConverter converter = new GradeConverter();
        HashMap<String, Double> totalStudentGrades = new HashMap<>();
        //average grade for student
        //counter for amount of deliverables
        int counter = 0;
        for (Deliverable d: course.getDeliverables()) {
            counter++;
            //calculate the average grade for each student
            //go through each of the submissions
            HashMap<String, Double> currentStudentGrades = d.accept(this);
            //add current student grade of deliverable to master list of each students grade
            currentStudentGrades.forEach((k, v) -> totalStudentGrades.put(k,totalStudentGrades.get(k)+v));

                    //get last submission of each of them
                    //check if convert is already in range


        }

 */


}
