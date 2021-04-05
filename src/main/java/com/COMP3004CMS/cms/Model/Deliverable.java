package com.COMP3004CMS.cms.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.UUID;

/*
    What is a Deliverable?
    Prof created requirements to be fulfilled and submitted in a course by a deadline and graded

    Deliverable Requirements:
    - needs to be created
    - needs to hold:
        * deliverable info
        * deadline
        * deliverable requirements slot (from prof)
        * submission slot (from student)
        * grade
    - needs to perform:
        * deliverable creation
        * deliverable update
        * deliverable submission
        * assign deliverable grade
*/
@Document(collection = "deliverable")
public class Deliverable {

    // deliverable variables
    @Id
    public String id;       //for MongoDB
    public String deliverableid;
    public String courseid;
    String title;
    String start;
    String deadline;
    // these two will definitely have to evolve into file-readers
    String requirements;

    ArrayList<User> students;
    //the submission and grade will have to be associate to student
    String submission;
    // in format [A-]
    String grade;


    // constructors
    public Deliverable() {
    }

    public Deliverable(String title, String start, String deadline) {
        this.title = title;
        this.start = start;
        this.deadline = deadline;
        requirements = "TO BE ANNOUNCED";
        submission = "STUDENT SUBMISSION SLOT";
        grade = "PENDING";
    }

    // *****  Prof Actions  *****

    public void submitDeadline(String newDate){
        deadline = newDate;
    }
    // Most Definitely will be a file in the future
    // public void submitRequirements(File newRequirements){ requirements = newRequirements; }
    public void submitRequirements(String newRequirements){
        requirements = newRequirements;
    }
    public void submitGrade(String newGrade){
        grade = newGrade;
    }

    // *****  Student Actions  *****

    public void submitSubmission(String newSubmission){
        submission = newSubmission;
    }

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

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getSubmission() {
        return submission;
    }

    public void setSubmission(String submission) {
        this.submission = submission;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDeliverableid() {
        return deliverableid;
    }

    public void setDeliverableid() {
        this.deliverableid = UUID.randomUUID().toString().replace("-","").substring(0,6);
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public ArrayList<User> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<User> students) {
        this.students = students;
        for (User s :this.students){
            s.setGrade(0);
            s.setSubmission("");
        }
    }

    public void addStudent(User s) {
        if (this.getStudents()==null) {
            this.setStudents(new ArrayList<User>());
        }
        try{
            if(!students.contains(s)) {
                s.setGrade(0);
                s.setSubmission("");
                students.add(s);
            }
        } catch (Exception e){
            System.out.println("Course addStudent - Error adding student");
            e.printStackTrace();
        }
    }

    public User findStudent(User stu){
        for(User student : this.getStudents()){
            if(student.equals(stu)){
                return student;
            }
        }
        return null;
    }

    public void initalSubmission(){
        for(User student : this.getStudents()){
            student.setSubmission("");
            student.setGrade(0);
        }
    }

    public void undateSubmissionByStudent(User stu){
        for(User student : this.getStudents()){
            if(student.equals(stu)){
                student.setSubmission(stu.getSubmission());
            }
        }
    }
    public void undateGradeByStudent(User stu, int grade){
        for(User student : this.getStudents()){
            if(student.equals(stu)){
                student.setGrade(grade);
            }
        }
    }
    @Override
    public boolean equals(Object obj) {
        boolean retVal = false;
        if (obj instanceof Deliverable){
            Deliverable ptr = (Deliverable) obj;
            retVal = ptr.deliverableid.equals(this.deliverableid);
        }
        return retVal;
    }

    @Override
    public String toString() {
        return title + " (start: " + start + " deadline: " + deadline+")";
    }
}
