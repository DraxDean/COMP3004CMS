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
        for (int i = 0; i < 500; i++){
            //add this object ever 5 indexes
            if (i %5 == 0){
                StudentInfo stu = new StudentInfo(25, "male");
                list.add(new GradeData(75, stu));
            } else
            if (i % 3 == 0){
                StudentInfo stu = new StudentInfo(35, "female");
                list.add(new GradeData(85, stu));

            } else {
                StudentInfo stu = new StudentInfo(15, "male");
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
        ReportRequest req = new ReportRequest(min, max,true,true,"ALL");
        ArrayList<GradeData> res = rg.getReport(req, list);

        // run seperate strategies
        SamplingStrategy avg = new AverageGradeSampling();
        SamplingStrategy skip = new SkipSampling();

        ArrayList<GradeData> processed = avg.getData(list);
        processed = skip.getData(processed);

        //see is seperate strategy matches combined strategy
        for (int i = 0; i < res.size(); i++){
            assertEquals(res.get(i).getGrade(), processed.get(i).getGrade(),"Checking if sampling strategies were properly combined");
        }

    }

    @Test
    void testGenderReportReq() {
        ReportRequest req = new ReportRequest(min, max,true,true,"MALE");
        ArrayList<GradeData> res = rg.getReport(req, list);

        // run seperate strategies
        SamplingStrategy avg = new AverageGradeSampling();
        SamplingStrategy skip = new SkipSampling();
        SamplingStrategy gen = new GenderSampling("male");

        ArrayList<GradeData> processed = gen.getData(list);
        processed = avg.getData(processed);
        //processed = skip.getData(processed);


        //see is seperate strategy matches combined strategy
        for (int i = 0; i < res.size(); i++){
            System.out.println(res.get(i).getGrade()+" == "+processed.get(i).getGrade());
            assertEquals(res.get(i).getGrade(), processed.get(i).getGrade(),"Checking if sampling strategies were properly combined");
        }

    }
}