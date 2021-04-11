package com.COMP3004CMS.cms.FactoryMethodDeliverable;

import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Storage.SubList;
import com.COMP3004CMS.cms.Storage.Submission;
import com.COMP3004CMS.cms.report.professorReprts.Visitor;
import com.COMP3004CMS.cms.utility.exceptions.MaxStudentSubmissions;

import java.util.ArrayList;
import java.util.HashMap;

public interface Deliverable {
    // *****  Prof Actions  *****

     void submitDeadline(String newDate);

     void submitRequirements(String newRequirements);
     void submitGrade(String newGrade);

    // *****  Student Actions  *****

     void submitSubmission(String newSubmission);

     String getId();

     void setId(String id);

     String getTitle();

     void setTitle(String title);

     String getStart();

     void setStart(String start);

     String getDeadline();

     void setDeadline(String deadline);

     String getRequirements();

     void setRequirements(String requirements);

     String getSubmission();

     void setSubmission(String submission);

     String getGrade();

     void setGrade(String grade);

     String getDeliverableid();

     void setDeliverableid();

     String getCourseid();

     void setCourseid(String courseid);

     ArrayList<User> getStudents();

     void setStudents(ArrayList<User> students);

     void addStudent(User s);


     User findStudent(User stu);

     void initalSubmission();

    void undateSubmissionByStudent(User stu);
    void undateGradeByStudent(User stu, int grade);




    /*


    Different function for changes

    */

    HashMap<String, SubList> getSubmissions();

    void studentSubmit(String studentId, Submission sub) throws MaxStudentSubmissions;

    void gradeDeliverable(User prof, String studentId, int grade);

    void gradeSubmition(String studentId, int grade);

    boolean checkProfessor(User prof);

    /* For visitor */
    HashMap<String,Double> accept(Visitor visitor);
}
