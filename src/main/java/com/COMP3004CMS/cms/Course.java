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
        waitlist = new ArrayList<Student>();
        deliverables = new ArrayList<Deliverable>();
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
    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Student> getWaitList() {
        return waitlist;
    }

    // Observer Design Pattern stuff
    public void notifyStudentsDeliverableCreated(Deliverable d){
        for (Student s : students){
            s.update("Deliverable " + d.title + " has been created.");
        }
    }
    public void notifyStudentsDeliverableGraded(Deliverable d){
        for (Student s : students){
            s.update("Deliverable " + d.title + " has been graded.");
        }
    }
    public void notifyStudentsDeliverableDeadlineExtended(Deliverable d){
        for (Student s : students){
            s.update("Deliverable " + d.title + " deadline has been extended to " + d.deadline);
        }
    }

    // ******  Prof Course Assignment  ******

    // Prof Applies
    public void addProfessor(Professor prof){
        try{
            professors.add(prof);
        } catch (Exception e){
            System.out.println("Error - adding Professor");
            e.printStackTrace();
        }

    }

    // Admin withdraw Prof from course
    public void removeProfessor(Professor toWithdraw){
        if (professorsAssigned.contains(toWithdraw)) {
            professorsAssigned.remove(professorsAssigned);
        }
        else {
            System.out.println("Error - Remove Professor: Prof not found in professors assigned to course");
        }
    }

    // *****  Student Enroll Sequence  ******

    /* Add student to course, waitlist if full*/
    public void addStudent(Student stu){
        try{
            if (students.size() > maxSeats){
                waitListStudent(stu);
            } else{
                students.add(stu);
                if(waitlist.contains(stu)) deWaitListStudent(stu);
            }
        } catch (Exception e){
            System.out.println("Course addStudent - Error adding student");
            e.printStackTrace();
        }
    }
    public void removeStudent(Student stu){
        try{
            students.remove(stu);
        } catch (Exception e){
            System.out.println("Course addStudent - Error removing student");
            e.printStackTrace();
        }
    }

    public void waitListStudent(Student stu){
        waitlist.add(stu);
    }
    public void deWaitListStudent(Student stu){
        waitlist.remove(stu);
    }

    public void addDeliverable(Deliverable newDeliverable) {
        deliverables.add(newDeliverable);
        notifyStudentsDeliverableCreated(newDeliverable);
    }
    public void deleteDeliverable(Deliverable newDeliverable) {
        deliverables.remove(newDeliverable);
    }

}
