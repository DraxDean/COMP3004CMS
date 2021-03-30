package com.COMP3004CMS.cms.Storage;

import java.lang.reflect.Type;
import java.util.Date;

/**
 *  Submission object that stores info about specific submission by a student
 * @param <E> Whatever docment type that is going to be upladed, can change based on requirments
 */
public class Submission<E>{
    String id;
    double grade;
    E doc;
    Date time;

    public Submission(String id, E doc, Date time) {
        this.id = id;
        this.grade = 0.0;
        this.doc = doc;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public double getGrade() {
        return grade;
    }

    public E getDoc() {
        return doc;
    }

    public Date getTime() {
        return time;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
