package com.COMP3004CMS.cms.report;

import java.util.ArrayList;

import static com.COMP3004CMS.cms.report.Colour.*;

public class GradeRangeSegregation implements  SamplingStrategy{

    private SamplingStrategy strategy = null;
    private int minViolationCounter = 0;
    private int maxViolationCounter = 0;
    private int minRange = 0;
    private int maxRange = 0;

    public GradeRangeSegregation(SamplingStrategy strategy, int minRange, int maxRange) {
        this.strategy = strategy;
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    @Override
    public ArrayList<GradeData> getData(ArrayList<GradeData> dIn){
        ArrayList<GradeData> out = strategy.getData(dIn);

        for (GradeData datum: out) {
            //set below threshhold colour
            if (datum.getGrade() < minRange){
                datum.setColour(RANGE_RED);
                minViolationCounter++;
            } else if (datum.getGrade() < maxRange){
                datum.setColour(RANGE_BLUE);
            } else {
                datum.setColour(RANGE_GREEN);
                maxViolationCounter++;
            }
        }

        return out;
    }

    public int getMinViolationCount() {
        return 0;
    }

    public int getMaxViolationCount() {
        return 0;
    }
}
