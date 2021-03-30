package com.COMP3004CMS.cms.utility;

import com.COMP3004CMS.cms.Storage.Submission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SubmissionTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getId() {

    }

    @Test
    void getGrade() {
    }

    @Test
    void setGrate() {
        Submission<Integer> s1= new Submission<Integer>("100", 100, new Date(12, Calendar.JULY,12));
        s1.setGrade(99.50);
        assertEquals(99.50, s1.getGrade(),"Checking if grades match");
        s1.setGrade(100);
        assertNotEquals(s1.getGrade(), 99.50, 0.0, "Checking if does not match old val");
        assertEquals(100, s1.getGrade(),"Checking if grades match");
    }
}