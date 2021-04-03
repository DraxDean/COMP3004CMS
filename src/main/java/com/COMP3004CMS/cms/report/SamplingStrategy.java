package com.COMP3004CMS.cms.report;

import com.COMP3004CMS.cms.report.GradeData;

import java.util.ArrayList;


public interface SamplingStrategy {
        public ArrayList<GradeData> getData(ArrayList<GradeData> dIn);
}


