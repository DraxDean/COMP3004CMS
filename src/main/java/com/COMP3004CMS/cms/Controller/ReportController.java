package com.COMP3004CMS.cms.Controller;

import com.COMP3004CMS.cms.AbstractFactoryDeliverable.Deliverable;
import com.COMP3004CMS.cms.AbstractFactoryDeliverable.DeliverableFactory;
import com.COMP3004CMS.cms.AbstractFactoryDeliverable.LongFactory;
import com.COMP3004CMS.cms.AbstractFactoryDeliverable.QuizFactory;
import com.COMP3004CMS.cms.Model.Course;
import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Model.submissionTypes.MultipleChoice;
import com.COMP3004CMS.cms.Storage.Submission;
import com.COMP3004CMS.cms.report.GradeData;
import com.COMP3004CMS.cms.report.ReportGenerator;
import com.COMP3004CMS.cms.report.ReportRequest;
import com.COMP3004CMS.cms.report.professorReports.CurrentGradeVisitor;
import com.COMP3004CMS.cms.report.professorReports.Visitor;
import com.COMP3004CMS.cms.utility.GradeConverter;
import com.COMP3004CMS.cms.utility.exceptions.InvalidSubmissionType;
import com.COMP3004CMS.cms.utility.exceptions.MaxStudentSubmissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.*;

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
            testsetUp();
            //send back last request
            model.addAttribute("req",req);
            System.out.println(req.skip);
            //check the gender type
            ReportGenerator report = new ReportGenerator();
            ArrayList<GradeData> product = report.getReport(req, new ArrayList<>());

            //get the average grades across all deliverables in the app
            Visitor gradeGetter = new CurrentGradeVisitor();

            HashMap<String, Double> courseGradesAvg = c1.accept(gradeGetter);


            /*
            * number of grades per range in course
            * ranges are for the letter grades, each key corresponds to a range ant int is amount of grades in that range
            * */
            TreeMap<String, Integer> gradeDistribution = new TreeMap<>();
            GradeConverter converter = new GradeConverter();
            courseGradesAvg.forEach((k,v) ->{
                String range = converter.convertToNumberRange(v);
                //check if range is already in map
                if (!gradeDistribution.containsKey(range)){
                    //if not add new range
                    gradeDistribution.put(range, 1);
                } else{
                    // else take out value and add one to it
                    gradeDistribution.put(range, (gradeDistribution.get(range)+1));
                }
            });
            //
            model.addAttribute("chartData", gradeDistribution);


        } catch (Exception e){
            e.printStackTrace();
        }
            return "reports";

    }
    //grade ranges
    int min = 50;
    int max = 100;

    Deliverable deliverable;
    Deliverable d2;
    Course c1;


    public void testsetUp() {
        c1 = new Course("1", "12345", "PSYC", "1001", "Psyc course", "D", "Winter", "1984");
        User prof = new User("2", "234", "bob", "prof", "PROFESSOR");
        c1.addProfessor(prof);
        for (int i = 300; i < 400; i++){
            User stu = new User(String.valueOf(i),String.valueOf(i*10) , "Firstname "+i, "Lastname "+i, "STUDENT");
            c1.addStudent(stu);
        }

        //add deliverables
        //Deliverable deliverable = new LongDeliverable("Deliverabe 1", "2001", "2002");
        DeliverableFactory factory = new LongFactory();
        deliverable = factory.create(c1.getCourseid(),"Deliverabe 1", "2001", "2002");
        deliverable.setRequirements("requirements");
        deliverable.setStudents(c1.getStudents());

        deliverable.getSubmissions().forEach((k,v)->{
            //System.out.println(k);
        });
        //add first deliverable for each student
        try{
            for (User u: deliverable.getStudents()) {
                deliverable.studentSubmit(u.getUserid(), new Submission("1", new ArrayList<String>(), new Date(2001)));
            }

        } catch (MaxStudentSubmissions em){
            System.out.println("Max reached");
        } catch (InvalidSubmissionType ev){
            System.out.println("Invalid reached");
        }

        //add deliverable to course
        c1.addDeliverable(deliverable);

        //professor grades deliverable
        for (int i = 300; i < 400; i++){
            //generate random grade
            int grade = (int) ((Math.random() * (max - min)) + min);

            deliverable.gradeDeliverable(prof,String.valueOf(i*10),grade);

        }

        /**
         *      #### SECOND DELIVERABLE ####
         */


        //add deliverables
        //Deliverable deliverable = new LongDeliverable("Deliverabe 1", "2001", "2002");
        DeliverableFactory factory2 = new QuizFactory();
        d2 = factory2.create(c1.getCourseid(),"Deliverabe 2", "2002", "2003");
        d2.setRequirements("requirements");
        d2.setStudents(c1.getStudents());

        d2.getSubmissions().forEach((k,v)->{
            //System.out.println(k);
        });
        //add first deliverable for each student
        try{
            for (User u: c1.getStudents()) {
                d2.studentSubmit(u.getUserid(), new Submission("1-1", new MultipleChoice("test"), new Date(2001)));
            }

        } catch (MaxStudentSubmissions  em){
            System.out.println("Max reached");
        } catch (InvalidSubmissionType ev){
            System.out.println("Invalid reached");
        }

        //add deliverable to course
        c1.addDeliverable(d2);

        //professor grades deliverable
        for (int i = 300; i < 400; i++){
            //generate random grade
            int grade = (int) ((Math.random() * (max - min)) + min);

            d2.gradeDeliverable(prof,String.valueOf(i*10),grade);

        }




    }
}
