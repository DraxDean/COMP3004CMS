package com.COMP3004CMS.cms.utility;

import com.COMP3004CMS.cms.Storage.SubList;
import com.COMP3004CMS.cms.Storage.Submission;
import com.COMP3004CMS.cms.utility.exceptions.MaxStudentSubmissions;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SubListTest {
    SubList sub = new SubList();
    @Before
    void setup(){

    }

    @Test
    void getSize() {
    }

    @Test
    void addBack() {
        Submission<String> str = new Submission<String>("123", "document", new Date(12, Calendar.JULY,12));
        ArrayList<Integer> arr1 = new ArrayList<Integer>();
        arr1.add(92726);
        Submission<ArrayList<Integer>> arr = new Submission<ArrayList<Integer>>("124", arr1, new Date(12, Calendar.JULY,12));
        try {
            sub.addBack(str);
            assertEquals("123",sub.getLast().getId());
            sub.addBack(arr);
            System.out.println(sub.getLast().getDoc().getClass());


        } catch (NullPointerException | MaxStudentSubmissions n){
            n.printStackTrace();
        }

    }

    @Test
    void addFront() {
    }
}