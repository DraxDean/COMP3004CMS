package com.COMP3004CMS.cms.Visitor;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Model.Deliverable;
import com.COMP3004CMS.cms.Model.Log;
import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class LogManager {

    @Autowired
    LogService logService;

    static ArrayList<String> logs;


    //constructors
    public LogManager(){
        logs = new ArrayList<>();
        List<Log> s = getLogs();
        for(Log log : s){
            logs.add(log.toString());
        }
    }
    public LogManager(List<Log> s){
        for(Log log : s){
            logs.add(log.toString());
        }
    }

    @PostMapping("/admin/logs/add")
    public void log(Course course) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        try {
            logService.addLog(new Log(course.getId(), timeStamp));
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    //post add deliverable
    @PostMapping("/dashboard/deliverable/add")
    public String postLog(Model model, Log log, BindingResult bindingResult) {
        logService.saveLog(log);
        return "redirect:/admin";
    }

    public void log(User user) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        try {
            Log log = new Log(user.getId(), timeStamp);
            System.out.println(log);
            logService.addLog(new Log(user.getId(), timeStamp));
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public List<Log> getLogs(){
        try {
            return logService.getLogs();
        }
        catch(Exception e) {
            return new ArrayList<Log>();
        }
    }
}
