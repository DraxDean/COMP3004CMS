package com.COMP3004CMS.cms;

import com.COMP3004CMS.cms.Course;
import junit.framework.TestCase;

public class CourseTest extends TestCase{

    // *****  Course Creation Testing  *****

    public void testCourseCreation() {
        Course newCourse = new Course();
        assertEquals(-1, newCourse.CNumber);
    }


    // *****  Professor Course Interaction Testing  *****

    public void testProfApply() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        newCourse.applyProfessor(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessorsApplied().size());
    }

    public void testDenyProfApply() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        newCourse.applyProfessor(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessorsApplied().size());

        newCourse.denyProfessor(newProfessor.getUserId());
        assertEquals(0, newCourse.getProfessorsApplied().size());
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
        assertEquals(0, newCourse.getProfessorsApplied().size());
    }

    public void testRemoveProf() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        // applying first
        newCourse.applyProfessor(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessorsApplied().size());

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

        newCourse.applyStudent(newStudent.getUserId());
        assertEquals(1, newCourse.getStudentsApplied().size());
    }

    public void testRejectStudentFromAppliedList() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.applyStudent(newStudent.getUserId());
        assertEquals(1, newCourse.getStudentsApplied().size());

        newCourse.rejectStudent(newStudent.getUserId());
        assertEquals(0, newCourse.getStudentsApplied().size());
    }

    public void testRejectStudentFromWaitList() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.waitListStudent(newStudent.getUserId());
        assertEquals(1, newCourse.getStudentsWaitListed().size());

        newCourse.rejectStudent(newStudent.getUserId());
        assertEquals(0, newCourse.getStudentsWaitListed().size());
    }

    public void testStudentWaitList() {
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

    public void testStudentEnrollFromWaitList() {
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

    public void testStudentWithdrawFromEnrolledList() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        // applying first
        newCourse.waitListStudent(newStudent.getUserId());
        assertEquals(1, newCourse.getStudentsWaitListed().size());

        // admin should enact this action
        newCourse.enrollStudent(newStudent.getUserId());
        assertEquals(1, newCourse.getStudentsEnrolled().size());
        assertEquals(0, newCourse.getStudentsApplied().size());

        // admin should enact this action
        newCourse.withdrawStudent(newStudent.getUserId());
        assertEquals(0, newCourse.getStudentsEnrolled().size());
    }
}
