package com.COMP3004CMS.cms;

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

import java.util.ArrayList;

public class Course {

    // class variables
    int CNumber;
    int maxSeats;
    String Department;
    String CarletonCode;
    String Title;
    String Description;

    // using userId's to avoid storing whole object
    ArrayList<Professor> professors;
    ArrayList<Integer> professorsAssigned;

    ArrayList<Student> students;
    ArrayList<Student> waitlist;

    ArrayList<Deliverable> deliverables;


    // constructor
    public Course () {
        CNumber = -1;
        maxSeats = 40;
        Department = "TEST";
        CarletonCode = "TEST1234";
        Title = "Data Structures and Algorithms, jk its a TEST";
        Description = "A course that makes you reconsider your degree.";

        // instantiate Lists
        professors = new ArrayList<Professor>();
        professorsAssigned = new ArrayList<>();

        students = new ArrayList<Student>();
        deliverables = new ArrayList<>();
    }

     /*
        User enroll Course workflow:

        User Applies to be created ->
        Admin Creates User ->
        User Logs in ->
        User Applies to be enrolled in a Course->
        User enrolls Student in course ->

     */

    // getters
    public ArrayList<Professor> getProfessors() {
        return professors;
    }
    public ArrayList<Integer> getProfessorsAssigned() {
        return professorsAssigned;
    }
    public ArrayList<Student> getStudentsEnrolled() {
        return students;
    }

    public ArrayList<Student> getWaitList() {
        return waitlist;
    }



    // ******  Prof Course Assignment  ******

    // Prof Applies
    public void addProf(Professor prof){
        try{
            professors.add(prof);
        } catch (Exception e){
            System.out.println("Error - Denying Professor: ID not found in Profs Applied List");
            e.printStackTrace();
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

    /* Add student to course, waitlist if full*/
    public void addStudent(Student stu){
        try{
            if (students.size() > maxSeats){
                waitlistStudent(stu);
            } else{
                students.add(stu);
            }
        } catch (Exception e){
            System.out.println("Course addStudent - Error adding student");
            e.printStackTrace();
        }
    }

    public void waitlistStudent(Student stu) throws Exception{
        waitlist.add(stu);
    }

    // Remove Student from ApplyList or WaitList
    /*
    public void rejectStudent(Student stu){
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
     */


    /*
    // Admin withdraws Student from course (withdrawal requests)
    public void withdrawStudent(Student toWithdraw){
        if (studentsEnrolled.contains(toWithdraw)){
            studentsEnrolled.remove(studentsEnrolled.indexOf(toWithdraw));
        }
        else{
            System.out.println("Error - Withdrawing Student to Course: Student ID not found in Students enrolled in Course");
        }
    }

     */

}
