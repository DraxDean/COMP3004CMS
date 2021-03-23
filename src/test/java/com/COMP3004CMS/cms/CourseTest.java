package com.COMP3004CMS.cms;

import junit.framework.TestCase;
import org.junit.Before;

public class CourseTest extends TestCase{
    Professor p1;
    Student s1;
    Course c1;

    @Before
    public void setUp(){
        Professor p1 = new Professor();
        c1 = new Course();
        for (int i = 0; i < 100; i++){
            Student stuN = new Student(i);
            c1.addStudent(stuN);
        }
    }



    // *****  Professor Course Interaction Testing  *****

    public void testProfApply() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        newCourse.addProf(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessors().size());
    }

    public void testDenyProfApply() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        newCourse.addProf(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessors().size());

        newCourse.denyProfessor(newProfessor.getUserId());
        assertEquals(0, newCourse.getProfessors().size());
    }

    public void testAssignProf() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        // applying first
        newCourse.addProf(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessors().size());

        // admin should enact this action
        newCourse.assignProfessor(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessorsAssigned().size());
        assertEquals(0, newCourse.getProfessors().size());
    }

    public void testRemoveProf() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        // applying first
        newCourse.addProf(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessors().size());

        // admin should enact this action
        newCourse.assignProfessor(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessorsAssigned().size());

        // admin should enact this action
        newCourse.withdrawProfessor(newProfessor.getUserId());
        assertEquals(0, newCourse.getProfessorsAssigned().size());
    }


    // *****  Student Course Interaction Testing  *****

    public void testStudentApply() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.add(newStudent);
        assertEquals(1, newCourse.getStudentsApplied().size());
    }

    public void testRejectStudentFromAppliedList() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.add(newStudent);
        assertEquals(1, newCourse.getStudentsApplied().size());

        newCourse.rejectStudent(newStudent);
        assertEquals(0, newCourse.getStudentsApplied().size());
    }

    public void testRejectStudentFromWaitList() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.waitListStudent(newStudent);
        assertEquals(1, newCourse.getStudentsWaitListed().size());

        newCourse.rejectStudent(newStudent);
        assertEquals(0, newCourse.getStudentsWaitListed().size());
    }

    public void testStudentWaitList() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.waitListStudent(newStudent);

        assertEquals(1, newCourse.getStudentsWaitListed().size());
    }

    public void testStudentEnrollFromApplied() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        // applying first
        newCourse.add(newStudent);
        assertEquals(1, newCourse.getStudentsApplied().size());

        // admin should enact this action
        newCourse.enrollStudent(newStudent);
        assertEquals(1, newCourse.getStudentsEnrolled().size());
        assertEquals(0, newCourse.getStudentsApplied().size());
    }

    public void testStudentEnrollFromWaitList() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        // applying first
        newCourse.waitListStudent(newStudent);
        assertEquals(1, newCourse.getStudentsWaitListed().size());

        // admin should enact this action
        newCourse.enrollStudent(newStudent);
        assertEquals(1, newCourse.getStudentsEnrolled().size());
        assertEquals(0, newCourse.getStudentsApplied().size());
    }

    public void testStudentWithdrawFromEnrolledList() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        // applying first
        newCourse.waitListStudent(newStudent);
        assertEquals(1, newCourse.getStudentsWaitListed().size());

        // admin should enact this action
        newCourse.enrollStudent(newStudent);
        assertEquals(1, newCourse.getStudentsEnrolled().size());
        assertEquals(0, newCourse.getStudentsApplied().size());

        // admin should enact this action
        newCourse.withdrawStudent(newStudent);
        assertEquals(0, newCourse.getStudentsEnrolled().size());
    }


    public void testGetStudent() {
        assertEquals("Check if student in index match stu dnumber",1, c1.getById(1));
    }
}
