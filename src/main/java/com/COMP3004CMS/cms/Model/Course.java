package com.COMP3004CMS.cms.Model;

/*
    Course Requirements:
    - needs to exist, ie be created
    - needs to hold:
        * course info
        * students
        * waitlisted students
        * professors
        * deliverables
    - needs to perform:
        * course creation
        * assign student
        * assign prof
        * course changes observer design pattern?
        * assign course grade
*/

import com.COMP3004CMS.cms.Deliverable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


@Document(collection = "courses")
public class Course {
    @Id
    public String id;           //for MongoDB
    @Size(min = 4, max=4)
    public String courseid;     //CNumber
    public String department;
    public char classCode;
    @Size(min = 4, max=4)
    public String coursecode;   //CarletonCode
    //rules for parameter requirments
    public String courseNum;
    public String title;
    public String description;
    public int maxSeats;
    private Time time;
    ArrayList<Integer> professorsApplied;
    ArrayList<Integer> professorsAssigned;
    ArrayList<Integer> studentsEnrolled;
    ArrayList<Integer> studentsApplied;
    ArrayList<Integer> studentsWaitListed;
    ArrayList<Deliverable> deliverables;
/*
    public Course() {
    }

    public Course(String courseid, String department ,String coursecode, String courseNum, char classCode, String title, int maxSeats, Time time) {
        this.courseid = courseid;
        this.department = department;
        this.coursecode = coursecode;
        this.courseNum = courseNum;
        this.classCode = classCode;
        this.title = title;
        this.maxSeats = maxSeats;
        this.time = time;
    }*/
    /*Temp constructor without time obj until it's added into creation on page*/
    public Course(String department ,String coursecode, String courseNum, char classCode, String title, int maxSeats) {
        this.courseid = "dontKnow";
        this.department = department;
        this.coursecode = coursecode;
        this.courseNum = courseNum;
        this.classCode = classCode;
        this.title = title;
        this.maxSeats = maxSeats;
        time = new Time(12,13, new Date(2020,Calendar.MARCH,21), new Date(2020, Calendar.MAY,22));
    }

    public ArrayList<Integer> getProfessorsApplied() {
        return professorsApplied;
    }

    public ArrayList<Integer> getProfessorsAssigned() {
        return professorsAssigned;
    }

    public ArrayList<Integer> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public ArrayList<Integer> getStudentsApplied() {
        return studentsApplied;
    }

    public ArrayList<Integer> getStudentsWaitListed() {
        return studentsWaitListed;
    }



    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + courseid + '\'' +
                ", department='" + department + '\'' +
                ", coursecode='" + coursecode + '\'' +
                ", title='" + title + '\'' +
                ", maxSeats=" + maxSeats +
                '}';
    }

    public void applyProfessor(int toApply){
        professorsApplied.add(toApply);
    }
    public void denyProfessor(int toDeny){
        if(professorsApplied.contains(toDeny)){
            professorsApplied.remove(professorsApplied.indexOf(toDeny));
        }
        else{
            System.out.println("Error - Denying Professor: ID not found in Profs Applied List");
        }
    }

    // Admin assigns Prof to course and removes from apply list
    public void assignProfessor(int toAssign){
        if (professorsApplied.contains(toAssign)) {
            professorsApplied.remove(professorsApplied.indexOf(toAssign));
            professorsAssigned.add(toAssign);
        }
        else{
            System.out.println("Error - Assigning professor: ID not found in Profs Applied List");
        }
    }

    // Admin withdraw Prof from course
    public void withdrawProfessor(int toWithdraw){
        if (professorsAssigned.contains(toWithdraw)) {
            professorsAssigned.remove(professorsAssigned.indexOf(toWithdraw));
        }
        else {
            System.out.println("Error - Withdraw Professor: Prof ID not found in professors assigned to course");
        }
    }



    // *****  Student Enroll Sequence  ******

    // Student applies to course
    public void applyStudent(int toApply){
        studentsApplied.add(toApply);
    }
    public void waitListStudent(int toWait){
        studentsWaitListed.add(toWait);
    }

    // Remove Student from ApplyList or WaitList
    public void rejectStudent(int toReject){
        if(studentsApplied.contains(toReject)){
            studentsApplied.remove(studentsApplied.indexOf(toReject));
        }
        else if(studentsWaitListed.contains(toReject)){
            studentsWaitListed.remove(studentsWaitListed.indexOf(toReject));
        }
        else{
            System.out.println("Error - Reject Student from Applied: Student ID not found in Students applied list or waitlist");
        }
    }

    // Admin enrolls Student in Course and removes from applied list or waitlist
    public void enrollStudent(int toAssign){
        if (studentsApplied.contains(toAssign)){
            studentsApplied.remove(studentsApplied.indexOf(toAssign));
            studentsEnrolled.add(toAssign);
        }
        else if (studentsWaitListed.contains(toAssign)){
            studentsWaitListed.remove(studentsWaitListed.indexOf(toAssign));
            studentsEnrolled.add(toAssign);
        }
        else{
            System.out.println("Error - Enrolling Student to Course: Student ID not in Students applied list or waitlist");
        }
    }

    // Admin withdraws Student from course (withdrawal requests)
    public void withdrawStudent(int toWithdraw){
        if (studentsEnrolled.contains(toWithdraw)){
            studentsEnrolled.remove(studentsEnrolled.indexOf(toWithdraw));
        }
        else{
            System.out.println("Error - Withdrawing Student to Course: Student ID not found in Students enrolled in Course");
        }
    }

    public CourseItem toPublic(){
        return new CourseItem(this.coursecode, this.courseNum, this.department, this.classCode, this.title, this.description, this.time, this.maxSeats);
    }


    public class CourseItem{
        private String code;
        private String number;
        private String department;
        private char classCode;
        private String title;
        private String description;
        private Time time;
        private int maxSeats;
        /*
        private String prerequisites;
        private String preclusions;
         */

        public CourseItem(String code, String number, String department, char classCode, String title, String description, Time time, int maxSeats) {
            this.code = code;
            this.number = number;
            this.department = department;
            this.classCode = classCode;
            this.title = title;
            this.description = description;
            this.time = time;
            this.maxSeats = maxSeats;
        }

        public String getCode() {
            return code;
        }

        public String getNumber() {
            return number;
        }

        public String getDepartment() {
            return department;
        }

        public char getClassCode() {
            return classCode;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public Time getTime() {
            return time;
        }

        public int getMaxSeats() {
            return maxSeats;
        }
    }
}


