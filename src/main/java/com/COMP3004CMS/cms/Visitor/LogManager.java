package com.COMP3004CMS.cms.Visitor;

import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Model.Student;
import com.COMP3004CMS.cms.Model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LogManager {
    static ArrayList<String> logs = new ArrayList<>();
    public void log(Course course) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        logs.add("time: " + timeStamp + ", course: "+ course.getDepartment() + course.getCoursecode());
    }

    public void log(User user) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        logs.add("time: " + timeStamp + ", user: "+ user.getName());
    }

    public ArrayList<String> getLogs(){
        return logs;
    }
}
