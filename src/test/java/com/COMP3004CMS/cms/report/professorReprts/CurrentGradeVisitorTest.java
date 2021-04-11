package com.COMP3004CMS.cms.report.professorReprts;


import com.COMP3004CMS.cms.AbstractFactoryDeliverable.Deliverable;
import com.COMP3004CMS.cms.AbstractFactoryDeliverable.DeliverableFactory;
import com.COMP3004CMS.cms.AbstractFactoryDeliverable.LongFactory;
import com.COMP3004CMS.cms.AbstractFactoryDeliverable.QuizFactory;
import com.COMP3004CMS.cms.Model.Course;

import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Model.submissionTypes.MultipleChoice;
import com.COMP3004CMS.cms.Storage.SubList;
import com.COMP3004CMS.cms.Storage.Submission;
import com.COMP3004CMS.cms.utility.exceptions.InvalidSubmissionType;
import com.COMP3004CMS.cms.utility.exceptions.MaxStudentSubmissions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

class CurrentGradeVisitorTest {
    //grade ranges
    int min = 50;
    int max = 100;

    Deliverable deliverable;
    Deliverable d2;
    Course c1;
    @BeforeEach
    void setUp() {
        c1 = new Course("1", "12345", "PSYC", "1001", "Psyc course", "D", "Winter", "1984");
        User prof = new User("2", "234", "bob", "prof", "PROFESSOR");
        c1.addProfessor(prof);
        for (int i = 300; i < 400; i++){
            User stu = new User(String.valueOf(i),String.valueOf(i*10) , "Firstname "+i, "Lastname "+i, "STUDENT");
            c1.addStudent(stu);
        }

        //add deliverables
        //Deliverable deliverable = new LongDeliverable("Deliverabe 1", "2001", "2002");
        DeliverableFactory factory = new LongFactory();
        deliverable = factory.create(c1.getCourseid(),"Deliverabe 1", "2001", "2002");
        deliverable.setRequirements("requirements");
        deliverable.setStudents(c1.getStudents());

        deliverable.getSubmissions().forEach((k,v)->{
            //System.out.println(k);
        });
        //add first deliverable for each student
        try{
            for (User u: deliverable.getStudents()) {
                deliverable.studentSubmit(u.getUserid(), new Submission("1", new ArrayList<String>(), new Date(2001)));
            }

        } catch (MaxStudentSubmissions em){
            System.out.println("Max reached");
        } catch (InvalidSubmissionType ev){
            System.out.println("Invalid reached");
        }

        //add deliverable to course
        c1.addDeliverable(deliverable);

        //professor grades deliverable
        for (int i = 300; i < 400; i++){
            //generate random grade
            int grade = (int) ((Math.random() * (max - min)) + min);

            deliverable.gradeDeliverable(prof,String.valueOf(i*10),grade);

        }

        /**
         *      #### SECOND DELIVERABLE ####
         */


        //add deliverables
        //Deliverable deliverable = new LongDeliverable("Deliverabe 1", "2001", "2002");
        DeliverableFactory factory2 = new QuizFactory();
        d2 = factory2.create(c1.getCourseid(),"Deliverabe 2", "2002", "2003");
        d2.setRequirements("requirements");
        d2.setStudents(c1.getStudents());

        d2.getSubmissions().forEach((k,v)->{
            //System.out.println(k);
        });
        //add first deliverable for each student
        try{
            for (User u: c1.getStudents()) {
                d2.studentSubmit(u.getUserid(), new Submission("1-1", new MultipleChoice("test"), new Date(2001)));
            }

        } catch (MaxStudentSubmissions  em){
            System.out.println("Max reached");
        } catch (InvalidSubmissionType ev){
            System.out.println("Invalid reached");
        }

        //add deliverable to course
        c1.addDeliverable(d2);

        //professor grades deliverable
        for (int i = 300; i < 400; i++){
            //generate random grade
            int grade = (int) ((Math.random() * (max - min)) + min);

            d2.gradeDeliverable(prof,String.valueOf(i*10),grade);

        }




    }

    @Test
    void testVisitLongDeliverable() {
        HashMap<String, SubList> test =  deliverable.getSubmissions();
        test.forEach((k,v)->{
            System.out.println(k+" , "+v.getLast().getGrade());
        });
        Visitor curr = new CurrentGradeVisitor();
        System.out.println(deliverable.accept(curr));

    }

    @Test
    void testVisitQuizDeliverable() {
        HashMap<String, SubList> test =  d2.getSubmissions();
        test.forEach((k,v)->{
            System.out.println(k+" , "+v.getLast().getGrade());
        });
        Visitor curr = new CurrentGradeVisitor();
        System.out.println(d2.accept(curr));
    }

    @Test
    void testVisitCourse() {
        Visitor curr = new CurrentGradeVisitor();
        System.out.println(c1.accept(curr));
    }
}