package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.report.ReportRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Controller
public class ReportController {

    @GetMapping("/reports")
    public String displayReports(Model model){
        ReportRequest req = new ReportRequest(0, 99, false, false, "All");
        model.addAttribute("req", req);
        return "reports";
    }

    @PostMapping ("/reports")
    public String getReports(
            ReportRequest req,
            BindingResult bindingResult,
            Model model) {
        try{
            //send back last request
            model.addAttribute("req",req);
            System.out.println(req.skip);
            //check the gender type
           if (req.gender.equals("MALE") ){
                System.out.println("Male Selected");
            }
            if (req.skip){
                System.out.println("Skip Selected");
            }
            if(req.avg){
                System.out.println("averae selected");
            }


        } catch (Exception e){
            e.printStackTrace();
        }
            return "reports";

    }
}
