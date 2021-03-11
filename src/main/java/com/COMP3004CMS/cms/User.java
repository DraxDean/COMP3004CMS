package com.COMP3004CMS.cms;

/*
    Functionalities for Login:
    - contain enough data to mock login at first
    - this class has login logic
 */

public abstract class User {

    // local variables
    int userId;
    boolean loggedIn;

    // will probably be passed login info
    public boolean LogIn(){
        // get login info from server
        // e.g. LogIn(data[] loginInfo)

        // set user variables (dummy for now)
        userId = -1;
        loggedIn = true;
        
        return loggedIn;
    }
}
