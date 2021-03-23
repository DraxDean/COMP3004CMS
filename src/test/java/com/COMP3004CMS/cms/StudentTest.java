package com.COMP3004CMS.cms;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentTest extends TestCase{
    Student stu;

    @BeforeEach
    public void setUp() {
        stu = new Student();
    }

    @Test
    public void getUserId() {
    }

    @Test
    public void getEmail() {
    }

    public void testCreation() {

        // from user parent class
        assertEquals(1, stu.getUserId());

        // from student child class
        assertEquals("1", stu.getEmail());

    }

    @Test
    public void testUpdate() {
        stu.update("Hello");
    }
}
