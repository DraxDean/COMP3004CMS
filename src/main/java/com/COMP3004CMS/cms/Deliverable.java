package com.COMP3004CMS.cms;

/*
    What is a Deliverable?
    Prof created requirements to be fulfilled and submitted in a course by a deadline and graded

    Deliverable Requirements:
    - needs to be created
    - needs to hold:
        * deliverable info
        * deadline
        * deliverable requirements slot (from prof)
        * submission slot (from student)
        * grade
    - needs to perform:
        * deliverable creation
        * deliverable update
        * deliverable submission
        * assign deliverable grade
*/

public class Deliverable {

    // deliverable variables
    String title;
    String start;
    String deadline;
    // these two will definitely have to evolve into file-readers
    String requirements;
    String submission;
    // in format [A-]
    String grade;

    // constructors
    public Deliverable () {
        title = "Dummy Deliverable";
        start = "11/11/11";
        deadline = "99/99/99";
        requirements = "TO BE ANNOUNCED";
        submission = "STUDENT SUBMISSION SLOT";
        grade = "PENDING";
    }
    public Deliverable (String titleC, String startC, String end) {
        title = titleC;
        start = startC;
        deadline = end;
        requirements = "TO BE ANNOUNCED";
        submission = "STUDENT SUBMISSION SLOT";
        grade = "PENDING";
    }

    // *****  Prof Actions  *****

    public void submitDeadline(String newDate){
        deadline = newDate;
    }
    // Most Definitely will be a file in the future
    // public void submitRequirements(File newRequirements){ requirements = newRequirements; }
    public void submitRequirements(String newRequirements){
        requirements = newRequirements;
    }
    public void submitGrade(String newGrade){
        grade = newGrade;
    }

    // *****  Student Actions  *****

    public void submitSubmission(String newSubmission){
        submission = newSubmission;
    }

}
