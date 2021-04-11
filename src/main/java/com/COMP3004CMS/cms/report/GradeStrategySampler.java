package com.COMP3004CMS.cms.report;

import java.util.ArrayList;

/**
 * Composite Sampling strategy to combine multiple strategies
 */
public class GradeStrategySampler implements SamplingStrategy{
    //strategy storage
    private ArrayList<SamplingStrategy> strategies = new ArrayList<SamplingStrategy>();

    @Override
    public ArrayList<GradeData> getData(ArrayList<GradeData> dIn){
        //implements each sampling strategy in order of add
        for (SamplingStrategy strategy: strategies) {
            dIn = strategy.getData(dIn);
        }
        return dIn;
    }

    public void addStrategy (SamplingStrategy strategy){
        strategies.add(strategy);
    }
}
