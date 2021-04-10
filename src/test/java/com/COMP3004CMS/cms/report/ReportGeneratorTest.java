package com.COMP3004CMS.cms.report;

import com.COMP3004CMS.cms.report.classRequirments.StudentInfo;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.COMP3004CMS.cms.report.Colour.RANGE_BLUE;
import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {
    ArrayList<GradeData> list;
    ReportGenerator rg;
    int min = 70;
    int max = 80;

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
       ArrayList<GradeData> res = rg.getReport(req, list);
        //go over new array and check if they are all 25
        for (GradeData r: res) {
            assertEquals(25,r.getStu().age);
            assertNotEquals(r.getStu().age, 35);
        }
        //test decorator is working
        for (GradeData r: res) {
            assertEquals(75,r.getGrade(),"checking if grade is 75 (middle range)");
            System.out.println(r.getColour());
            assertEquals(RANGE_BLUE,r.getColour(), "Checking if middle range grade gets corresponding colour label");
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