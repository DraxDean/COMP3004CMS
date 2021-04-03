package com.COMP3004CMS.cms.report;


import com.COMP3004CMS.cms.report.classRequirments.StudentInfo;

import java.util.ArrayList;

/**
 * Sampling strategy for average grade over samplerate students (using 6 for now)
 */
public class AverageGradeSampling implements SamplingStrategy {
    @Override
    public ArrayList<GradeData> getData(ArrayList<GradeData> dIn) {
        int sampleRate = 6;
        double sample = 0;
        ArrayList<GradeData> dataOut = new ArrayList<GradeData>();

        try{
            for (int i = 1; i <= dIn.size(); i++){
                //need to get first index
                sample+= dIn.get(i-1).getGrade();

                if (i % sampleRate == 0){
                    //add a new GradeData point with the average grade and age, can't do it with gender
                    GradeData d = new GradeData((sample/sampleRate), new StudentInfo(0,"N/A"));
                    dataOut.add(d);
                    sample = 0;
                }
            }
        //catching if index goes out of bounds on lastone
        } catch (IndexOutOfBoundsException ei){

        }

        return dataOut;
    }
}
