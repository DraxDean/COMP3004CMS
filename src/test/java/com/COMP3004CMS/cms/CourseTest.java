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

    public void testAddProf() {
        c1.addProfessor(p1);
        assertEquals(1, c1.getProfessors().size());
    }

    public void testAssignProf() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        newCourse.addProfessor(newProfessor);
        assertEquals(1, newCourse.getProfessors().size());

    }

    public void testRemoveProf() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        // applying first
        newCourse.addProfessor(newProfessor);
        assertEquals(1, newCourse.getProfessors().size());

        // admin should enact this action
        newCourse.removeProfessor(newProfessor);
        assertEquals(0, newCourse.getProfessorsAssigned().size());
    }

    // *****  Student Course Interaction Testing  *****

    public void testStudentApply() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.addStudent(newStudent);
        assertEquals(1, newCourse.getStudents().size());
    }

    public void deWaitListStudent() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.waitListStudent(newStudent);
        assertEquals(1, newCourse.getWaitList().size());

        newCourse.deWaitListStudent(newStudent);
        assertEquals(0, newCourse.getWaitList().size());
    }

    public void testStudentWaitList() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.waitListStudent(newStudent);
        assertEquals(1, newCourse.getWaitList().size());
    }

    public void testStudentAdd() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        // applying first
        newCourse.addStudent(newStudent);
        assertEquals(1, newCourse.students.size());
    }

    public void testStudentEnrollFromWaitList() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        // applying first
        newCourse.waitListStudent(newStudent);
        assertEquals(1, newCourse.getWaitList().size());

        // admin should enact this action
        newCourse.addStudent(newStudent);
        assertEquals(1, newCourse.students.size());
        assertEquals(0, newCourse.waitlist.size());
    }

    public void testRemoveStudentFromCourse() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        // admin should enact this action
        newCourse.addStudent(newStudent);
        assertEquals(1, newCourse.students.size());

        // admin should enact this action
        newCourse.removeStudent(newStudent);
        assertEquals(0, newCourse.getStudents().size());
    }
}
