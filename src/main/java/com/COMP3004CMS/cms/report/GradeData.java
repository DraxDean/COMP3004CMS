package com.COMP3004CMS.cms.report;

import com.COMP3004CMS.cms.report.classRequirments.StudentInfo;

/**
 * Class that contains the data to be analyzed
 */
public class GradeData {
    double grade;
    StudentInfo stu;

    public GradeData(double grade, StudentInfo stu) {
        this.grade = grade;
        this.stu = stu;
    }
}
