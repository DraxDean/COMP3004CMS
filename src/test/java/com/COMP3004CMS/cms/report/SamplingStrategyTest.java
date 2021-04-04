package com.COMP3004CMS.cms.report;

import com.COMP3004CMS.cms.report.classRequirments.StudentInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.COMP3004CMS.cms.report.Colour.*;
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
            assertEquals(25,datum.getStu().age);
            assertNotEquals(datum.getStu().age, 35);
        }
    }

    @Test
    void testAverageSampling() {
        //run the average algorithm
        //go through results and see if each datapoint = every 6 (66,66,85,66,76,66)
        //process data
        AverageGradeSampling avg = new AverageGradeSampling();
        ArrayList<GradeData> processed = avg.getData(list);
        //go over new array and check if they are all 25
        assertEquals((433.0/6),processed.get(0).getGrade());
        assertEquals((443.0/6),processed.get(1).getGrade());

    }

    @Test
    void testRangeSegregation() {
        //create sampling strategy,
        SkipSampling skip = new SkipSampling();
        //wrap sampling strategy
        GradeRangeSegregation deco = new GradeRangeSegregation(skip, 75,80);


        //call getdata on sampling strategy
        ArrayList<GradeData> processed = deco.getData(list);
        //check is samplig number good
        for (GradeData datum: processed) {
            assertEquals(75,datum.getGrade(),"checking if grade is 75 (middle range)");
            assertEquals(RANGE_BLUE,datum.getColour(), "Checking if middle range grade gets corresponding colour label");
        }
    }

    @Test
    void testRangeSegregationAllRanges() {
        AverageGradeSampling avg = new AverageGradeSampling();
        GradeRangeSegregation deco = new GradeRangeSegregation(avg, 73,75);

        ArrayList<GradeData> processed = deco.getData(list);

        for (GradeData datum: processed) {

            //check if range min is good
            if (datum.getGrade() < 73){
                assertEquals(RANGE_RED,datum.getColour(), "Checking if low range grade gets corresponding colour label");
            }
            //check if mid range is good,
            if (datum.getGrade() > 73 && datum.getGrade() < 75 ){
                assertEquals(RANGE_BLUE,datum.getColour(), "Checking if med range grade gets corresponding colour label");
            }
            //check if rangeMax is good,
            if (datum.getGrade() >  75 ){
                assertEquals(RANGE_GREEN,datum.getColour(), "Checking if high range grade gets corresponding colour label");
            }
        }
    }

    @Test
    void testRangeSegregationRangeCounts() {
        AverageGradeSampling avg = new AverageGradeSampling();
        GradeRangeSegregation deco = new GradeRangeSegregation(avg, 73,75);

        ArrayList<GradeData> processed = deco.getData(list);

        //Check Counts for ranges
        int min = 0;
        int max = 0;
        for (GradeData datum: processed) {

            //add min counter
            if (datum.getGrade() < 73){
                min++;
            }

            //add max counter
            if (datum.getGrade() >  75 ){
                max++;
            }
        }
        //check if min count is good
        assertEquals(min,deco.getMinViolationCount(), "Checking if min count is correct");
        //check maxcount
        assertEquals(max,deco.getMaxViolationCount(), "Checking if max count is correct");
    }

    @Test
    void testCombinationStrategySampler() {
        AverageGradeSampling avg = new AverageGradeSampling();
        SkipSampling skip = new SkipSampling();
        GradeStrategySampler combined = new GradeStrategySampler();

        //create regular skip sampling
        ArrayList<GradeData> processed = avg.getData(list);
        processed = skip.getData(processed);
        for (GradeData datum: processed) {
            //System.out.println(datum.getGrade());
        }

        //print the average so can see
        combined.addStrategy(avg);
        combined.addStrategy(skip);
        //get pattern so can test
        ArrayList<GradeData> combinedRes = combined.getData(list);
        //run the combined stratefy and see if they are the same
        for (int i = 0; i < combinedRes.size(); i++){
            assertEquals(processed.get(i).getGrade(), combinedRes.get(i).getGrade(),"Checking if sampling strategies were properly combined");
        }
    }
}