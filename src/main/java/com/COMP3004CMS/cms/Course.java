package com.COMP3004CMS.cms;

/*
    Course Requirements:
    - needs to exist, ie be created
    - needs to hold:
        * course info
        * students
        * waitlisted students
        * professors
        * deliverables to hand out
        * deliverables handed in
        * grades for deliverable (might be encapuslated)
    - needs to perform:
        * course creation
        * assign student
        * assign prof
        * deliverable creation
        * deliverable submission
        * assign deliverable grade
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
    ArrayList<Integer> professorsApplied;
    ArrayList<Integer> professorsAssigned;

    ArrayList<Integer> studentsEnrolled;
    ArrayList<Integer> studentsApplied;
    ArrayList<Integer> studentsWaitListed;

    ArrayList<Deliverable> deliverables;


    // constructor
    public Course () {
        CNumber = -1;
        maxSeats = 40;
        Department = "TEST";
        CarletonCode = "TEST1234";
        Title = "Data Structures and Algorithms, jk its a TEST";
        Description = "A course that makes you reconsider your degree.";
    }

     /*
        User enroll Course workflow:

        User Applies to be created ->
        Admin Creates User ->
        User Logs in ->
        User Applies to be enrolled in a Course->
        User enrolls Student in course ->

     */


    // ******  Prof Course Assignment  ******

    // Prof Applies
    public void applyProfessor(int toApply){
        professorsApplied.add(toApply);
    }
    public void denyProfessor(int toDeny){
        if(professorsApplied.contains(toDeny)){
            professorsApplied.remove(toDeny);
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
        studentsApplied.add(toWait);
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

}
