package com.COMP3004CMS.cms;

import junit.framework.TestCase;

public class DeliverableTest extends TestCase {

    // *****  Deliverable Creation Testing  *****

    public void testDeliverableCreation() {
        Course newCourse = new Course();
        assertEquals(-1, newCourse.CNumber);

        Deliverable newDeliverable = new Deliverable();
        assertEquals("Dummy Deliverable", newDeliverable.title);

        // add to course
        newCourse.deliverables.add(newDeliverable);
        assertEquals(1, newCourse.deliverables.size());
    }


    // *****  Professor Deliverable Interaction Testing  *****

    public void testSubmitRequirements() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        newCourse.applyProfessor(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessorsApplied().size());

        Deliverable newDeliverable = new Deliverable();
        newCourse.deliverables.add(newDeliverable);
        assertEquals(1, newCourse.deliverables.size());
        // now you have a course with a prof and deliverable

        newDeliverable.submitRequirements("TEST REQUIREMENTS");
        assertEquals("TEST REQUIREMENTS", newDeliverable.requirements);
    }

    public void testSubmitDeadline() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        newCourse.applyProfessor(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessorsApplied().size());

        Deliverable newDeliverable = new Deliverable();
        newCourse.deliverables.add(newDeliverable);
        assertEquals(1, newCourse.deliverables.size());
        // now you have a course with a prof and deliverable

        newDeliverable.submitDeadline("TE/ST/ER");
        assertEquals("TE/ST/ER", newDeliverable.deadline);
    }

    public void testSubmitGrade() {
        Professor newProfessor = new Professor();
        Course newCourse = new Course();

        newCourse.applyProfessor(newProfessor.getUserId());
        assertEquals(1, newCourse.getProfessorsApplied().size());

        Deliverable newDeliverable = new Deliverable();
        newCourse.deliverables.add(newDeliverable);
        assertEquals(1, newCourse.deliverables.size());
        // now you have a course with a prof and deliverable

        newDeliverable.submitGrade("Z+");
        assertEquals("Z+", newDeliverable.grade);
    }


    // *****  Student Deliverable Interaction Testing  *****

    public void testStudentAccessRequirements() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.applyStudent(newStudent);
        newCourse.enrollStudent(newStudent);
        assertEquals(1, newCourse.getStudentsEnrolled().size());

        Deliverable newDeliverable = new Deliverable();
        newCourse.deliverables.add(newDeliverable);
        assertEquals(1, newCourse.deliverables.size());
        newDeliverable.submitRequirements("TEST REQUIREMENTS");

        // now you have a course with a Student and deliverable with requirements

        assertEquals("TEST REQUIREMENTS", newDeliverable.requirements);
    }

    public void testSubmitSubmission() {
        Student newStudent = new Student();
        Course newCourse = new Course();

        newCourse.applyStudent(newStudent);
        newCourse.enrollStudent(newStudent);
        assertEquals(1, newCourse.getStudentsEnrolled().size());

        Deliverable newDeliverable = new Deliverable();
        newCourse.deliverables.add(newDeliverable);
        assertEquals(1, newCourse.deliverables.size());
        // now you have a course with a Student and deliverable

        //get requirements

        newDeliverable.submitSubmission("TEST SUBMISSION");
        assertEquals("TEST SUBMISSION", newDeliverable.submission);
    }
}