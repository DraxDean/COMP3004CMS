package com.COMP3004CMS.cms.report;

import java.util.ArrayList;

public class GradeRangeSegregation implements  SamplingStrategy{

    private SamplingStrategy strategy = null;
    private int minViolationCounter = 0;
    private int maxViolationCounter = 0;
    private int min = 0;
    private int max = 0;

    public GradeRangeSegregation(SamplingStrategy strategy, int min, int max) {
        this.strategy = strategy;
        this.min = min;
        this.max = max;
    }

    @Override
    public ArrayList<GradeData> getData(ArrayList<GradeData> dIn){
        ArrayList<GradeData> out = strategy.getData(dIn);

        for (GradeData datum: out) {
            if (datum.getGrade())
        }
    }
}
