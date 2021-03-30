package com.COMP3004CMS.cms.Model;

import com.COMP3004CMS.cms.Storage.SubList;
import com.COMP3004CMS.cms.Storage.Submission;
import com.COMP3004CMS.cms.utility.exceptions.MaxStudentSubmissions;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Dictionary;

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
@Document(collection = "deliverable")
public class Deliverable {

    @Id
    // deliverable variables
    public String id;       //for MongoDB
    String title;
    Date start;
    Date deadline;
    // these two will definitely have to evolve into file-readers
    String requirements;
    Dictionary<Integer, SubList> submissions;
    // in format [A-]
    String grade;



    // constructors
    public Deliverable() {}


    public Deliverable(String title, Date start, Date deadline, Dictionary<Integer, SubList> submissions) {
        this.title = title;
        this.start = start;
        this.deadline = deadline;
        requirements = "TO BE ANNOUNCED";
        this.submissions = submissions;
        grade = "PENDING";
    }


    // *****  Prof Actions  *****

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
    /*
    public void setSubmission(String submission) {
        this.submission = submission;
    }*/

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void submitDeadline(Date newDate){
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

    public void submitSubmission(String studentId, Submission doc) throws MaxStudentSubmissions {
        //check to see if student already submitted anything
        try {
            SubList stuSubs = submissions.get(studentId);
            if ( stuSubs == null){
                stuSubs = new SubList();
            }
            stuSubs.addBack(doc);
        } catch (NullPointerException en){
            System.out.println("Error with added student submition to deliverable");
        } catch (MaxStudentSubmissions em){
            throw new MaxStudentSubmissions("Submition error for student id: "+studentId);
        }
    }
}
