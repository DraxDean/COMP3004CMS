package com.COMP3004CMS.cms;

/*
    Is the actual instantiable version of a User
    Student Requirements for Login:
    - needs to exist, ie be created
    - needs to hold enough data to login to the program and
        get to homepage with student data
 */

import com.COMP3004CMS.cms.utility.Subscriber;

import java.util.ArrayList;

public class Student extends User implements Subscriber {
    String email;
    String password; // might be handled by security module

    //  empty constructor
    public Student (){
        // for testing purposes
        email = "1";
        userId = 1;
    }


    //  making a student from admin request student (note: password is sketchy)
    public Student (String email, String password){
        email = email;
        password = password;
    }


    public int getUserId(){
        return userId;
    }
    public ArrayList<String> getAnnouncements(){
        return announcements;
    }
    public String getEmail(){
        return email;
    }

    @Override
    public void update(String s) {
       announcements.add(s);
    }
}
