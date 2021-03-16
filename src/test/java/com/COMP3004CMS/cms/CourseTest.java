package com.COMP3004CMS.cms;

import junit.framework.TestCase;

public class CourseTest extends TestCase{

    public void testCourseCreation() {
        Course newCourse = new Course();
        assertEquals(-1, newCourse.CNumber);
    }

    public void testProfApply() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        newCourse.applyProfessor(newProfessor.getUserId());

        assertEquals(1, newCourse.getProfessorsApplied().size());
    }
    public void testAssignProf() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        // applying first
        newCourse.applyProfessor(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessorsApplied().size());

        // admin should enact this action
        newCourse.assignProfessor(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessorsAssigned().size());
    }

    public void testStudentApply() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.applyStudent(newStudent.getUserId());

        assertEquals(1, newCourse.getStudentsApplied().size());
    }

    public void testStudentWaitlist() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.waitListStudent(newStudent.getUserId());

        assertEquals(1, newCourse.getStudentsWaitListed().size());
    }

    public void testStudentEnrollFromApplied() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        // applying first
        newCourse.applyStudent(newStudent.getUserId());
        assertEquals(1, newCourse.getStudentsApplied().size());

        // admin should enact this action
        newCourse.enrollStudent(newStudent.getUserId());
        assertEquals(1, newCourse.getStudentsEnrolled().size());
        assertEquals(0, newCourse.getStudentsApplied().size());
    }

    public void testStudentEnrollFromWaitlist() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        // applying first
        newCourse.waitListStudent(newStudent.getUserId());
        assertEquals(1, newCourse.getStudentsWaitListed().size());

        // admin should enact this action
        newCourse.enrollStudent(newStudent.getUserId());
        assertEquals(1, newCourse.getStudentsEnrolled().size());
        assertEquals(0, newCourse.getStudentsApplied().size());
    }
}
