package com.COMP3004CMS.cms.utility;

public class GradeConverter {
    public String convertToLetter(double grade) {
        if(grade>=90 && grade<=100){ return "A+";}
        else if (grade>=85 && grade<90){ return "A";}
        else if (grade>=80 && grade<85){ return "A-";}
        else if (grade>=77 && grade<80){ return "B+";}
        else if (grade>=73 && grade<77){ return "B";}
        else if (grade>=70 && grade<73){ return "B-";}
        else if (grade>=67 && grade<70){ return "C+";}
        else if (grade>=63 && grade<67){ return "C";}
        else if (grade>=60 && grade<63){ return "C-";}
        else if (grade>=57 && grade<60){ return "D+";}
        else if (grade>=53 && grade<57){ return "D";}
        else if (grade>=50 && grade<53){ return "D-";}
        else if (grade>=0 && grade<50){ return "F";}
        else {
            return "error";
        }
    }

    public String convertToNumberRange(double grade) {
        if(grade>=90 && grade<=100){ return "90 - 100";}
        else if (grade>=85 && grade<90){ return "85 - 90";}
        else if (grade>=80 && grade<85){ return "80 - 85";}
        else if (grade>=77 && grade<80){ return "77 - 80";}
        else if (grade>=73 && grade<77){ return "73 - 77";}
        else if (grade>=70 && grade<73){ return "70 - 73";}
        else if (grade>=67 && grade<70){ return "67 - 70";}
        else if (grade>=63 && grade<67){ return "63 - 67";}
        else if (grade>=60 && grade<63){ return "60 - 63";}
        else if (grade>=57 && grade<60){ return "57 - 60";}
        else if (grade>=53 && grade<57){ return "53 - 57";}
        else if (grade>=50 && grade<53){ return "50 - 53";}
        else if (grade>=0 && grade<50){ return "0 - 50";}
        else {
            return "error";
        }
    }
}
