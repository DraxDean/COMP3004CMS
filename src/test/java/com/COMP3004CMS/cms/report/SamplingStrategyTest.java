package com.COMP3004CMS.cms.report;

import com.COMP3004CMS.cms.report.classRequirments.StudentInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SamplingStrategyTest {
    ArrayList<GradeData> list;

    @BeforeEach
    void setUp() {
        list = new ArrayList<GradeData>();
        for (int i = 0; i < 100; i++){
            //add this object ever 5 indexes
            if (i %5 == 0){
                StudentInfo stu = new StudentInfo(25, "male");
                list.add(new GradeData(75, stu));
            } else
            if (i % 3 == 0){
                StudentInfo stu = new StudentInfo(35, "female");
                list.add(new GradeData(85, stu));

            } else {
                StudentInfo stu = new StudentInfo(15, "other");
                list.add(new GradeData(66, stu));
            }
        }
    }

    @Test
    void TestSkipData() {
        //process data
        SkipSampling skip = new SkipSampling();
        ArrayList<GradeData> processed = skip.getData(list);
        //go over new array and check if they are all 25
        for (GradeData datum: processed) {
            assertEquals(25,datum.stu.age);
            assertNotEquals(datum.stu.age, 35);
        }
    }
}