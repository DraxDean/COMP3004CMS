package com.COMP3004CMS.cms.Model;

import com.COMP3004CMS.cms.utility.Subscriber;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User implements Subscriber{
    @Id
    public String id;
    public String userid;       //student id
    public String username;
    public String password;
    public String firstname;
    public String lastname;
    private String roles;
    List<Course> courseList;
    ArrayList<String> announcements;

    public String submission;
    public int grade;

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String id, String userid, String firstname, String lastname, String roles) {
        this.id = id;
        this.userid = userid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = roles;
    }

    public User(String id, String userid, String username, String password, String firstname, String lastname, String roles) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = roles;
    }

    public void addCourse(Course course){
        if (this.getCourseList()==null) {
            this.setCourseList(new ArrayList<Course>());
        }
        if (!this.courseList.contains(course)){
            courseList.add(course);
        }
    }

    public void dropCourse(Course course){
        if (this.getCourseList()!=null) {
            if (this.getCourseList().contains(course)){
                this.courseList.remove(course);
            }
        }
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public ArrayList<String> getAnnouncements() {
        if (announcements==null){
            this.announcements = new ArrayList<>();
        }
        return announcements;
    }

    public void setAnnouncements(ArrayList<String> announcements) {
        this.announcements = announcements;
    }

    public void addAnnouncements(String announcements) {
        if (this.announcements==null){
            this.announcements = new ArrayList<>();
        }
        this.announcements.add(announcements);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getSubmission() {
        return submission;
    }

    public void setSubmission(String submission) {
        this.submission = submission;
    }

    public void undateGradeByCourse(Course course, double grade){
        for(Course c : this.getCourseList()){
            if(c.equals(course)){
                c.setGrade(convertToLetter(grade));
            }
        }
    }

    private String convertToLetter(double grade) {
        if(grade>=90 && grade<=100){ return "A+";}
        else if (grade>=85 && grade<90){ return "A";}
        else if (grade>=80 && grade<85){ return "A-";}
        else if (grade>=77 && grade<80){ return "B+";}
        else if (grade>=73 && grade<77){ return "B";}
        else if (grade>=70 && grade<73){ return "B-";}
        else if (grade>=67 && grade<70){ return "C+";}
        else if (grade>=63 && grade<67){ return "C";}
        else if (grade>=60 && grade<63){ return "C-";}
        else if (grade>=57 && grade<60){ return "D+";}
        else if (grade>=53 && grade<57){ return "D";}
        else if (grade>=50 && grade<53){ return "D-";}
        else if (grade>=0 && grade<50){ return "F";}
        else {
            return "error";
        }
    }

    @Override
    public void update(String s) {
        if(announcements==null){
            this.setAnnouncements(new ArrayList<>());
        }
        announcements.add(s);
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;

        if (obj instanceof User){
            User ptr = (User) obj;
            equals = ptr.userid.equals(this.userid);
        }
        return equals;
    }

    @Override
    public String toString() {
        return String.format(
                "[First Name: %s, Last Name: %s, Student Number: %s]",
                firstname, lastname, userid);
    }

    public String getName() {
        return String.format("%s %s", firstname, lastname);
    }
}
