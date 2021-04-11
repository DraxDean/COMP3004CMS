package com.COMP3004CMS.cms.report;

import java.util.ArrayList;

/**
 * Sampling strategy for gender, contains a string that it compares to datapoint student gender string
 */
public class GenderSampling implements SamplingStrategy{
    String gender;

    public GenderSampling(String gender) {
        this.gender = gender.toLowerCase();
    }

    @Override
    public ArrayList<GradeData> getData(ArrayList<GradeData> dIn) {
        ArrayList<GradeData> dataOut = new ArrayList<GradeData>();


        //catching if index goes out of bounds on lastone
        try{
            for (GradeData datum: dIn) {
                String check = datum.getStu().gender;
                if (check.toLowerCase() == gender){
                    dataOut.add(datum);
                }
            }
        } catch (NullPointerException en){
            en.printStackTrace();
        }

        return dataOut;
    }
}
