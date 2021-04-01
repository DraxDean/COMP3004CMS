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

    public void addCourse(Course course){
        if (this.getCourseList()==null) {
            this.setCourseList(new ArrayList<Course>());
        }
        courseList.add(course);
    }
    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof User){
            User ptr = (User) v;
            retVal = ptr.userid.equals(this.userid);
        }
        return retVal;
    }
    public void dropCourse(Course course){
        courseList.remove(course);
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return String.format(
                "[First Name: %s, Last Name: %s, Student Number: %s]",
                firstname, lastname, userid);
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
        return announcements;
    }

    public void setAnnouncements(ArrayList<String> announcements) {
        this.announcements = announcements;
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

    @Override
    public void update(String s) {
        announcements.add(s);
    }


}
