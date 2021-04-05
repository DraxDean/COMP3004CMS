package com.COMP3004CMS.cms.Model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.validation.constraints.NotNull;
import java.util.Date;


public class Time {
    //Validation stuff for for objects being created
    @NotNull
    private int startTime;
    @NotNull
    private int endTime;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;

    public Time(int startTime, int endTime, Date startDate, Date endDate) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
