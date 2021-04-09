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
        model.addAttribute("gender", "ALL");
        model.addAttribute("skip", false);
        model.addAttribute("avg", false);

        return "reports";
    }

    @PostMapping ("/reports")
    public String getReports(
            ReportRequest req,
            /*@RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "skip", required = false) boolean skip,
            @RequestParam(value = "avg", required = false) boolean avg,
            @RequestParam(value = "minGrade", required = false) double minGrade,
            @RequestParam(value = "minGrade", required = false) double maxGrade,

             */
            BindingResult bindingResult,
            Model model)

        {
        try{
            System.out.println(req.skip);
            //check the gender type
           if (req.gender.equals("MALE") ){
                System.out.println("Male Selected");
                model.addAttribute("gender", "MALE");
            }
            if (req.skip){
                System.out.println("Skip Selected");
                model.addAttribute("skip", true);
            }
            if(req.avg){
                System.out.println("averae selected");
                model.addAttribute("avg", true);
            }


        } catch (Exception e){
            e.printStackTrace();
        }
            return "reports";

    }
}
