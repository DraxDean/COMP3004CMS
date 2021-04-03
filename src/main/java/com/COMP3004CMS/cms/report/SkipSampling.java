package com.COMP3004CMS.cms.report;

import java.util.ArrayList;

public class SkipSampling implements SamplingStrategy{
    @Override
    public ArrayList<GradeData> getData(ArrayList<GradeData> dIn) {
        int skipRate = 10;
        ArrayList<GradeData> dataOut = new ArrayList<GradeData>();
        int size = dIn.size();

        //catching if index goes out of bounds on lastone
        try{
            for (int i = 0; i < size; i+= skipRate){
                dataOut.add(dIn.get(i));
            }
        } catch (IndexOutOfBoundsException ei){

        }

        return dataOut;
    }
}