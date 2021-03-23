package com.COMP3004CMS.cms;

/*
    Is the actual instantiable version of a user for Professor
    Prof Requirements for Login:
    - needs to exist, ie be created
    - needs to hold enough data to login to the program and
        get to homepage with student data
 */

public class Professor extends User{
    String email;
    String password; // might be handled by security module

    //  empty constructor
    public Professor (){
        // for testing purposes
        email = "1";
        userId = 1;
    }

    public int getUserId(){
        return userId;
    }
    public String getEmail(){
        return email;
    }
}

