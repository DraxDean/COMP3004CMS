package com.COMP3004CMS.cms;

/*
    Is the actual instantiable version of a User
    Student Requirements for Login:
    - needs to exist, ie be created
    - needs to hold enough data to login to the program and
        get to homepage with student data
 */

public class Student extends User{
    String email;
    String password; // might be handled by security module

    //  empty constructor
    public Student (){
    }

    //  making a student from admin request student (note: password is sketchy)
    public Student (String email, String password){
        email = email;
        password = password;
    }
}
