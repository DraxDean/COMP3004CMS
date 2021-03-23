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

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "courses")
public class Course {
    @Id
    public String id;
    public String department;
    public String coursecode;
    public String title;
    public int maxSeats;

    public Course() {
    }

    public Course(String id, String department, String coursecode, String title, int maxSeats) {
        this.id = id;
        this.department = department;
        this.coursecode = coursecode;
        this.title = title;
        this.maxSeats = maxSeats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", department='" + department + '\'' +
                ", coursecode='" + coursecode + '\'' +
                ", title='" + title + '\'' +
                ", maxSeats=" + maxSeats +
                '}';
    }
}


