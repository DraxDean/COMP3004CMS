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

import com.COMP3004CMS.cms.report.professorReports.Visitor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.COMP3004CMS.cms.AbstractFactoryDeliverable.Deliverable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


@Document(collection = "courses")
public class Course {
    @Id
    public String id;           //for MongoDB
    public String courseid;     //CNumber
    public String department;
    public String coursecode;   //CarletonCode
    public String section;
    public String title;
    public String description;
    public int maxSeats;
    public String term;
    public String year;
    public String grade;

    public String status;       //for displaying "register" or "drop"
    public String action;
    // using userId's to avoid storing whole object
    User professor;
    ArrayList<User> professors;
    ArrayList<Integer> professorsAssigned;

    ArrayList<User> students;
    ArrayList<User> waitlist;

    ArrayList<Deliverable> deliverables;


    public Course() {
    }

    public Course(String id, String courseid, String department, String coursecode, String title, int maxSeats) {
        this.id = id;
        this.courseid = courseid;
        this.department = department;
        this.coursecode = coursecode;
        this.title = title;
        this.maxSeats = maxSeats;
    }

    public Course(String id, String courseid, String department, String coursecode, String title, String section, String term, String year) {
        this.id = id;
        this.courseid = courseid;
        this.department = department;
        this.coursecode = coursecode;
        this.title = title;
        this.section = section;
        this.term = term;
        this.year = year;
        //cant add any student is not set
        maxSeats = 200;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public ArrayList<User> getProfessors() {
        return professors;
    }
    public ArrayList<Integer> getProfessorsAssigned() {
        return professorsAssigned;
    }
    public ArrayList<User> getStudents() {
        return students;
    }
    public User getStudentsByUid(String userid) {
        for(User student: this.students){
            if(userid.equals(student.getUserid())){
                return student;
            }
        }
        return null;
    }

    public ArrayList<User> getWaitList() {
        return waitlist;
    }

    // Observer Design Pattern stuff

    public void notifyStudentsDeliverableCreated(Deliverable d){
        for (User s : students){
            s.update("Deliverable " + d.getTitle() + " has been created.");
        }
    }

    public void notifyStudentsDeliverableDeleted(Deliverable d){
        for (User s : students){
            s.update("Deliverable " + d.getTitle() + " has been deleted.");
        }
    }
    
    public void notifyStudentsDeliverableGraded(Deliverable d){
        for (User s : students){
            s.update("Deliverable " + d.getTitle() + " has been graded.");
        }
    }
    public void notifyStudentsDeliverableDeadlineExtended(Deliverable d){
        for (User s : students){
            s.update("Deliverable " + d.getTitle() + " deadline has been extended to " + d.getDeadline());
        }
    }


    // ******  Prof Course Assignment  ******

    // Prof Applies
    public void addProfessor(User prof){
        if (this.getProfessors()==null) {
            this.setProfessors(new ArrayList<User>());
        }
        try{
            if (!professors.contains(prof)) {
                professors.add(prof);
            }
        } catch (Exception e){
            System.out.println("Error - adding Professor");
            e.printStackTrace();
        }

    }

    // Admin withdraw Prof from course
    public void removeProfessor(User toWithdraw){
        if (professorsAssigned.contains(toWithdraw)) {
            professorsAssigned.remove(professorsAssigned);
        }
        else {
            System.out.println("Error - Remove Professor: Prof not found in professors assigned to course");
        }
    }

    // *****  Student Enroll Sequence  ******

    /* Add student to course, waitlist if full*/
    public void addStudent(User stu){
        if (this.getStudents()==null) {
            this.setStudents(new ArrayList<User>());
        }
        try{
            if (students.size() > maxSeats){
                if (this.getWaitList()==null) {
                    this.setWaitlist(new ArrayList<User>());
                }
                waitListStudent(stu);
            }else if (students.contains(stu)){
            }else{
                students.add(stu);
                if(waitlist!=null) {
                    if (waitlist.contains(stu)) deWaitListStudent(stu);
                }
            }
        } catch (Exception e){
            System.out.println("Course addStudent - Error adding student");
            e.printStackTrace();
        }
    }

    public void removeStudent(User stu){
        try{
            students.remove(stu);
        } catch (Exception e){
            System.out.println("Course addStudent - Error removing student");
            e.printStackTrace();
        }
    }

    public void waitListStudent(User stu){
        waitlist.add(stu);
    }
    public void deWaitListStudent(User stu){
        waitlist.remove(stu);
    }

    public void addDeliverable(Deliverable newDeliverable) {
        if(deliverables==null){
            this.setDeliverables(new ArrayList<>());
        }
        deliverables.add(newDeliverable);
        notifyStudentsDeliverableCreated(newDeliverable);

    }
    public void deleteDeliverable(Deliverable newDeliverable) {
        deliverables.remove(newDeliverable);
        notifyStudentsDeliverableDeleted(newDeliverable);
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

    public void setCourseid(){
        this.courseid = UUID.randomUUID().toString().replace("-","").substring(0,6);
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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setProfessors(ArrayList<User> professors) {
        this.professors = professors;
    }

    public void setProfessorsAssigned(ArrayList<Integer> professorsAssigned) {
        this.professorsAssigned = professorsAssigned;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStudents(ArrayList<User> students) {
        this.students = students;
    }

    public ArrayList<User> getWaitlist() {
        return waitlist;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setWaitlist(ArrayList<User> waitlist) {
        this.waitlist = waitlist;
    }

    public ArrayList<Deliverable> getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(ArrayList<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public User getProfessor() {
        return professor;
    }

    public void setProfessor(User professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return department+coursecode+section+" "+title+" "
                +term+" "+year;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof Course){
            Course ptr = (Course) v;
            retVal = ptr.courseid.equals(this.courseid);
        }
        return retVal;
    }

    public CourseItem toPublic(){
            return new CourseItem(this.courseid, this.department, this.coursecode, this.title, this.description, this.maxSeats);
    }

    /** #####################
     *      FOR VISITOR
      #####################*/
    public HashMap<String, Double> accept(Visitor visitor) {
        return visitor.visitCourse(this);
    }


    public class CourseItem{ //for MongoDB
        public String courseid;     //CNumber
        public String department;
        public String coursecode;   //CarletonCode
        public String title;
        public String description;
        public int maxSeats;

        public CourseItem(String courseid, String department, String coursecode, String title, String description, int maxSeats) {
            this.courseid = courseid;
            this.department = department;
            this.coursecode = coursecode;
            this.title = title;
            this.description = description;
            this.maxSeats = maxSeats;
        }
    }

    public void undateGradeByStudent(User stu, int grade){
        for(User student : this.getStudents()){
            if(student.equals(stu)){
                student.setGrade(grade);
            }
        }
    }
}
