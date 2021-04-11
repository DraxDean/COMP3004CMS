package com.COMP3004CMS.cms.AbstractFactoryDeliverable;

import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Model.submissionTypes.LongAnswer;
import com.COMP3004CMS.cms.Model.submissionTypes.MixedFormat;
import com.COMP3004CMS.cms.Model.submissionTypes.MultipleChoice;
import com.COMP3004CMS.cms.Storage.SubList;
import com.COMP3004CMS.cms.Storage.Submission;
import com.COMP3004CMS.cms.report.professorReprts.Visitor;
import com.COMP3004CMS.cms.utility.exceptions.InvalidSubmissionType;
import com.COMP3004CMS.cms.utility.exceptions.MaxStudentSubmissions;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Quiz implements Deliverable{
    // deliverable variables
    @Id
    public String id;       //for MongoDB
    public String deliverableid;
    public String courseid;
    private String title;
    private String start;
    private String deadline;
    private double weight;
    // these two will definitely have to evolve into file-readers

    ArrayList<User> students;
    //the submission and grade will have to be associate to student
    String submission;
    // in format [A-]
    String grade;

    Object[] requirments = {};
    HashMap<String, SubList> submissions;



    public Quiz(String courseId, String title, String start, String deadline) {
        this.courseid = courseId;
        this.title = title;
        this.start = start;
        deliverableid = UUID.randomUUID().toString().replace("-","").substring(0,6);
        this.deadline = deadline;
        this.weight = weight;
        submissions = new HashMap<>();
    }

    public Quiz(String courseId, String title, String start, String deadline, double weight) {
        this.courseid = courseId;
        this.title = title;
        this.start = start;
        deliverableid = UUID.randomUUID().toString().replace("-","").substring(0,6);
        this.deadline = deadline;
        this.weight = weight;
        submissions = new HashMap<>();
    }
    // *****  Prof Actions  *****



    // *****  Student Actions  *****


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDeliverableid() {
        return deliverableid;
    }


    public String getCourseid() {
        return courseid;
    }

    public void setStudents(ArrayList<User> students) {
        for (User s :students){
            //add a key for each student id, value is list of all their submissions
            submissions.put(s.getUserid(), new SubList());
        }
    }

    public void addStudent(User s) {
        try{
            if(!findByUserId(s.getUserid())) {
                submissions.put(s.getUserid(), new SubList());
            }
        } catch (Exception e){
            System.out.println("Course addStudent - Error adding student");
            e.printStackTrace();
        }
    }

    @Override
    public User findStudent(User user) {
        return null;
    }

    public boolean findByUserId(String user){ return submissions.containsKey(user); }



    /* rquired Methods */
    @Override
    public void setRequirements(String requirements) {

    }

    @Override
    public void setGrade(String grade) {

    }

    @Override
    public ArrayList<User> getStudents() {
        return null;
    }

    @Override
    public void setDeliverableid() {

    }

    @Override
    public void initalSubmission() {

    }

    @Override
    public void undateSubmissionByStudent(User stuSubmission) {

    }

    @Override
    public void undateGradeByStudent(User student, int grade) {

    }




    /*


    Different function for changes

    */

    public HashMap<String, SubList> getSubmissions() {
        return submissions;
    }

    public void studentSubmit(String studentId, Submission sub) throws MaxStudentSubmissions, InvalidSubmissionType {
        try{
            if(checkSubmission(sub)) {
                SubList studentSubs = submissions.get(studentId);
                studentSubs.addBack(sub);
                submissions.put(studentId, studentSubs);
            } else {
                throw new InvalidSubmissionType("Error - Incompatable file type "+sub.getClass());
            }
        }
        catch (NullPointerException en){
            en.printStackTrace();
        }
    }

    /**
     * Checks to see if submission type is part of allowed submissions
     * @param sub submitions that user is entering
     * @return true if valid, false otherwise
     */
    private boolean checkSubmission(Submission sub) {
        /*for (Object o: requirments) {
            if (o.getClass() == sub.getClass()){
                return true;
            }
        }*/
        return true;
    }

    public void gradeDeliverable(User prof, String studentId, int grade){
        if (checkProfessor(prof)){
            gradeSubmition(studentId, grade);
        }
    }

    public void gradeSubmition(String studentId, int grade) {
        try{
            //find submission list that corrsponds to userId
            SubList studentSubs = submissions.get(studentId);
            //get submission and grade it
            if (studentSubs.getLast() == null){

            }
            Submission sub = studentSubs.getLast();
            sub.setGrade(grade);
            submissions.put(studentId, studentSubs);
        }
        catch (NullPointerException en){
            en.printStackTrace();
        }
    }

    public boolean checkProfessor(User prof) {
        return prof.getRoles().equals("PROFESSOR");
    }

    @Override
    public HashMap<String, Double> accept(Visitor visitor) {
        return visitor.visitQuiz(this);
    }

}
