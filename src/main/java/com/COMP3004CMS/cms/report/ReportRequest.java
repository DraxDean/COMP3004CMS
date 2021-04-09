package com.COMP3004CMS.cms.report;

/**
 * Object containing parameters required for report request
 */
public class ReportRequest {
    public double minRange;
    public double maxRange;
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
    public ReportRequest(double minRange, double maxRange, boolean skip, boolean avg, String gender) {
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.skip = skip;
        this.avg = avg;
        this.gender = gender;
    }
}
