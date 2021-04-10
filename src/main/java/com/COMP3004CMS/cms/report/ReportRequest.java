package com.COMP3004CMS.cms.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * Object containing parameters required for report request
 */
public class ReportRequest {
    public int minRange;
    public int maxRange;
    public boolean skip;
    public boolean avg;
    public String gender;

    /* Empty constructer for when sending to page for first time
    public ReportRequest(){
        this.minRange = 0;
        this.maxRange = 0;
        this.skip = false;
        this.avg = false;
        this.gender = "ALL";
    }

     */

    /* constructor contaning data for checkbox binding after request is made*/
    public ReportRequest(int minRange, int maxRange, boolean skip, boolean avg, String gender) {
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.skip = skip;
        this.avg = avg;
        this.gender = gender;
    }

    /**
     * Convert variable into general input that can be used to call the appropriate functions
     * @return HashMap containing a key for every algorithm type required along with the values required for the algorithm to run
     */
    public HashMap<String, Integer> getRequest(){
        HashMap<String, Integer> out = new HashMap<>();
        try{
            //add min range
            out.put("minRange", this.minRange);
            //add max range
            out.put("maxRange", this.maxRange);
            //add gender is contain
            switch (gender.toLowerCase()){
                case "male":
                    out.put("gender", 1);
                    break;

                case "female":
                    out.put("gender", 2);
                    break;
            }

            //add skip if contains
            if (skip){
                out.put("skip", 1);
            }
            //add avg if contains
            if (avg){
                out.put("avg", 1);
            }
        } catch (NullPointerException en){
            System.out.println("Error: ReportRequest: get request, nullpointer");
            en.printStackTrace();
        }
        return out;
    }
}
