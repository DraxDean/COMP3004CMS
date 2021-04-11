package com.COMP3004CMS.cms.report.professorReprts;


import com.COMP3004CMS.cms.FactoryMethodDeliverable.Deliverable;
import com.COMP3004CMS.cms.FactoryMethodDeliverable.DeliverableFactory;
import com.COMP3004CMS.cms.FactoryMethodDeliverable.LongDeliverable;
import com.COMP3004CMS.cms.FactoryMethodDeliverable.LongFactory;
import com.COMP3004CMS.cms.Model.Course;

import com.COMP3004CMS.cms.Model.Professor;
import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Storage.SubList;
import com.COMP3004CMS.cms.Storage.Submission;
import com.COMP3004CMS.cms.utility.exceptions.MaxStudentSubmissions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CurrentGradeVisitorTest {
    //grade ranges
    int min = 50;
    int max = 100;

    Deliverable deliverable;
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
        deliverable = factory.create("Deliverabe 1", "2001", "2002");
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
        }

        //add deliverable to course
        c1.addDeliverable(deliverable);

        //professor grades deliverable
        for (int i = 300; i < 400; i++){
            //generate random grade
            int grade = (int) ((Math.random() * (max - min)) + min);

            deliverable.gradeDeliverable(prof,String.valueOf(i*10),grade);

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
}