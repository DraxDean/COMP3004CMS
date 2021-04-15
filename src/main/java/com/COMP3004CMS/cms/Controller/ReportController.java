package com.COMP3004CMS.cms.Controller;


import com.COMP3004CMS.cms.report.GradeData;
import com.COMP3004CMS.cms.report.ReportGenerator;
import com.COMP3004CMS.cms.report.ReportRequest;

import com.COMP3004CMS.cms.report.classRequirments.StudentInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Controller
public class ReportController {
    ArrayList<GradeData> list;

    @GetMapping("/reports")
    public String displayReports(Model model) {
        ReportRequest req = new ReportRequest(0, 99, false, false, "All");
        model.addAttribute("req", req);
        return "reports";
    }

    @PostMapping("/reports")
    public String getReports(
            ReportRequest req,
            BindingResult bindingResult,
            Model model) {
        try {
            setUp();
            //send back last request
            model.addAttribute("req", req);
            System.out.println(req.skip);
            //check the gender type
            ReportGenerator report = new ReportGenerator();
            ArrayList<GradeData> product = report.getReport(req, list);
            model.addAttribute("stats", product);


        } catch (Exception e ){
            e.printStackTrace();
        }
        return "reports";
    }

    public void setUp() {
        list = new ArrayList<>();

        for (int i = 0; i < 100; i++){
            //add this object ever 5 indexes
            if (i %5 == 0){
                StudentInfo stu = new StudentInfo(25, "male");
                list.add(new GradeData(75, stu));
            } else
            if (i % 3 == 0){
                StudentInfo stu = new StudentInfo(35, "female");
                list.add(new GradeData(85, stu));

            } else {
                StudentInfo stu = new StudentInfo(15, "other");
                list.add(new GradeData(66, stu));
            }
        }
    }
}


