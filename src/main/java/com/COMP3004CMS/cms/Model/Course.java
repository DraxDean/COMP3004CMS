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

import com.COMP3004CMS.cms.Model.Deliverable;
import com.COMP3004CMS.cms.Model.Professor;
import com.COMP3004CMS.cms.Model.Student;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


@Document(collection = "courses")
public class Course {
    @Id
    public String id;           //for MongoDB
    public String courseid;     //CNumber
    public String department;
    public String coursecode;   //CarletonCode
    public String title;
    public String description;
    public int maxSeats;

    // using userId's to avoid storing whole object
    ArrayList<Professor> professors;
    ArrayList<Integer> professorsAssigned;

    ArrayList<Student> students;
    ArrayList<Student> waitlist;

    ArrayList<Deliverable> deliverables;


    public Course() {
    }

    public Course(String courseid, String department, String coursecode, String title, int maxSeats) {
        this.courseid = courseid;
        this.department = department;
        this.coursecode = coursecode;
        this.title = title;
        this.maxSeats = maxSeats;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
