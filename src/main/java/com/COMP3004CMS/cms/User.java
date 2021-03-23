package com.COMP3004CMS.cms;

/*
    Functionalities for Login:
    - contain enough data to mock login at first
    - this class has login logic
 */

public abstract class User {

    // local variables
    int userId = -1;
    boolean loggedIn = false;

    // will probably be passed login info
    public boolean LogIn(){
        // get login info from server
        // e.g. LogIn(data[] loginInfo)

        // set user variables (dummy for now)
        userId = 101074102;
        loggedIn = true;
        
        return loggedIn;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId(){
        return userId;
    }

}
