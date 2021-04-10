package com.COMP3004CMS.cms.report;

import com.COMP3004CMS.cms.report.classRequirments.StudentInfo;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {
    ArrayList<GradeData> list;
    ReportGenerator rg;
    double min = 50;
    double max = 75;

    @BeforeEach
    public void setUp() {
        list = new ArrayList<GradeData>();
        rg = new ReportGenerator();
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
    void testSkipReportReq() {
       ReportRequest req = new ReportRequest(min, max,true,false,"ALL");
       ArrayList<GradeData> res = rg.getReport(req);
        //go over new array and check if they are all 25
        for (GradeData r: res) {
            System.out.println(r.getGrade());
            assertEquals(25,r.getGrade());
            assertNotEquals(r.getStu().age, 35);
        }

    }

    @Test
    void testAvgReportReq() {
    }

    @Test
    void testGenderReportReq() {
    }

    @Test
    void test2ReportReq() {
    }

    @Test
    void test3ReportReq() {
    }
}