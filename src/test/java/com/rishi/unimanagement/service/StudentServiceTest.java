package com.rishi.unimanagement.service;

import com.rishi.unimanagement.data.StudentData;
import com.rishi.unimanagement.repo.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentRepository = Mockito.mock(StudentRepository.class);
        studentService = StudentService.getInstance();
    }

    @Test
    void testAddStudent() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);
        StudentData student = new StudentData("John Doe", "password123", 101, grades);

        studentService.addStudent(student);
        Mockito.verify(studentRepository).addUser(student);
    }

    @Test
    void testGetStudentCGPA() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);
        grades.put("Science", 80);
        StudentData student = new StudentData("John Doe", "password123", 101, grades);

        Mockito.when(studentRepository.getUserByName("John Doe")).thenReturn(student);

        double cgpa = studentService.getStudentCGPA("John Doe");
        assertEquals(85.0, cgpa, 0.01);
    }
}
