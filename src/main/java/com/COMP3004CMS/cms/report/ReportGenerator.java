package com.COMP3004CMS.cms.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ReportGenerator {

    public ArrayList<GradeData> getReport(ReportRequest req, ArrayList<GradeData> dataIn){
        HashMap<String,Integer> request = req.getRequest();
        //build report generator
        GradeStrategySampler strategies = new GradeStrategySampler();
        //default ranges in case of error
        AtomicInteger minRange = new AtomicInteger();
        AtomicInteger maxRange = new AtomicInteger(100);

        //add algorithm strategies to component
        request.forEach((key, value) -> {
           switch (key){

               case "avg":
                   SamplingStrategy avg = new AverageGradeSampling();
                   strategies.addStrategy(avg);
                   break;

               case "skip":
                   SamplingStrategy skip = new SkipSampling();
                   strategies.addStrategy(skip);
                   break;

               case "gender":
                   if (value == 1){
                       SamplingStrategy male = new GenderSampling("MALE");
                       strategies.addStrategy(male);
                   } else {
                       SamplingStrategy female = new GenderSampling("FEMALE");
                       strategies.addStrategy(female);
                   }
                   break;

               case "minRange":
                   minRange.set(value);
                   break;

               case "maxRange":
                   maxRange.set(value);
                   break;
           }
        });

        //decorate strategies with grade range segregator
        GradeRangeSegregation deco = new GradeRangeSegregation(strategies, minRange.get(), maxRange.get());
        //return processed report
        return deco.getData(dataIn);
    }


}
