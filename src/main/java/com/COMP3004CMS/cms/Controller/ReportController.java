package com.COMP3004CMS.cms.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
public class ReportController {

    @GetMapping("/reports")
    public String displayReports(){
        System.out.println("Getting the reports page");
        return "reports";
    }

    @PostMapping ("/getreport")
    public String getReports(Model model){
        try{
            System.out.println("Printing map");
            Map<String, Object> modelMap = model.asMap();
            modelMap.forEach((key,value) -> System.out.println("Key: "+key+" Value: "+value));
            //check the gender type
            if (modelMap.get("gender")=="MALE"){
                System.out.println("Male Selected");
            }
            else if (modelMap.get("skip") != null){
                System.out.println("Skip Selected");
            } if(modelMap.get("avg") != null){
                System.out.println("averae selected");
            }
            //check the skip sampling
            //check the average sampling
            System.out.println("Asking for reports called");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            return "reports";
        }
    }
}
