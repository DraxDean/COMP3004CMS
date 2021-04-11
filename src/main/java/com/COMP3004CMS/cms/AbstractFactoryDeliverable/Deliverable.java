package com.COMP3004CMS.cms.AbstractFactoryDeliverable;

import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Storage.SubList;
import com.COMP3004CMS.cms.Storage.Submission;
import com.COMP3004CMS.cms.report.professorReports.Visitor;
import com.COMP3004CMS.cms.utility.exceptions.InvalidSubmissionType;
import com.COMP3004CMS.cms.utility.exceptions.MaxStudentSubmissions;

import java.util.ArrayList;
import java.util.HashMap;

public interface Deliverable {
    public String getId();

    public void setId(String id);

    public String getTitle();

    public void setTitle(String title);

    public String getStart();

    public void setStart(String start);

    public String getDeadline();

    public void setDeadline(String deadline);

    public String getDeliverableid();


    public String getCourseid();

    public void setStudents(ArrayList<User> students);

    public void addStudent(User s);

    public User findStudent(User user);

    boolean findByUserId(String userId);

    /* Additional required */
    void setRequirements(String requirements);

    void setGrade(String grade);

    ArrayList<User> getStudents();

    void setDeliverableid();

    void initalSubmission();

    void undateSubmissionByStudent(User stuSubmission);

    void undateGradeByStudent(User student, int grade);









    HashMap<String, SubList> getSubmissions();

    void studentSubmit(String studentId, Submission sub) throws MaxStudentSubmissions, InvalidSubmissionType;

    void gradeDeliverable(User prof, String studentId, int grade);

    String[] gradeSubmition(String studentId, int grade);

    boolean checkProfessor(User prof);

    /* For visitor */
    HashMap<String,Double> accept(Visitor visitor);


}
