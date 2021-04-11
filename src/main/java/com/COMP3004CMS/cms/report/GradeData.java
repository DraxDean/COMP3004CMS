package com.COMP3004CMS.cms.report;

import com.COMP3004CMS.cms.report.classRequirments.StudentInfo;

;
/**
 * Class that contains the data to be analyzed
 */
public class GradeData {
    private double grade;
    private StudentInfo stu;
    private Colour colour;


    public GradeData(double grade, StudentInfo stu) {
        this.grade = grade;
        this.stu = stu;
        colour = null;
    }

    public double getGrade() {return grade;}

    public void setGrade(double grade) {this.grade = grade;}

    public StudentInfo getStu() {return stu;}

    public void setStu(StudentInfo stu) {this.stu = stu;}

    public Colour getColour() {return colour;}

    public void setColour(Colour colour) {this.colour = colour;}
}
