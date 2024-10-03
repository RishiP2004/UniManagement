package com.rishi.unimanagement.service;

import com.rishi.unimanagement.data.StudentData;
import com.rishi.unimanagement.repo.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    private StudentService studentService;
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);

        studentService = StudentService.getInstance();
        studentService.setRepository(studentRepository);
    }

    @Test
    void testAddStudent() {
        Map<String, Integer> grades = Map.of("Math", 90);
        StudentData student = new StudentData("John", "password123", 101, grades);

        studentService.addStudent(student);
        verify(studentRepository).addUser(student);
    }

    @Test
    void testGetStudentByName() {
        Map<String, Integer> grades = Map.of("Math", 90);
        StudentData student = new StudentData("John", "password123", 101, grades);

        when(studentRepository.getUserByName("John")).thenReturn(student);

        StudentData retrievedStudent = studentService.getStudentByName("John");

        assertNotNull(retrievedStudent);
        assertEquals("John", retrievedStudent.getName());
        assertEquals("password123", retrievedStudent.getPassword());
        verify(studentRepository).getUserByName("John");
    }

    @Test
    void testUpdatePassword_Success() {
        Map<String, Integer> grades = Map.of("Math", 90);
        StudentData student = new StudentData("John", "oldPassword", 101, grades);

        when(studentRepository.getUserByName("John")).thenReturn(student);
        studentService.updatePassword("John", "newPassword");
        assertEquals("newPassword", student.getPassword());
        verify(studentRepository).updateUser(student);
    }

    @Test
    void testUpdatePassword_Failure() {
        when(studentRepository.getUserByName("Unknown")).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> studentService.updatePassword("Unknown", "newPassword"));
    }

    @Test
    void testGetStudentsBySection() {
        Map<String, Integer> grades = Map.of("Math", 90);
        StudentData student1 = new StudentData("John", "password123", 101, grades);
        StudentData student2 = new StudentData("Jane", "password456", 101, grades);

        when(studentRepository.getAllUsers()).thenReturn(Arrays.asList(student1, student2));

        List<String> studentsInSection = studentService.getStudentsBySection(101);

        assertEquals(2, studentsInSection.size());
        assertTrue(studentsInSection.contains("John"));
        assertTrue(studentsInSection.contains("Jane"));
        verify(studentRepository).getAllUsers();
    }

    @Test
    void testUpdateGrade() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);

        StudentData student = new StudentData("John", "password123", 101, grades);

        when(studentRepository.getUserByName("John")).thenReturn(student);
        studentService.updateGrade("John", "Math", 95);
        assertEquals(95, student.getGrade("Math"));
        verify(studentRepository).updateUser(student);
    }

    @Test
    void testDeleteStudent() {
        studentService.deleteStudent("John");

        verify(studentRepository).deleteUser("John");
    }

    @Test
    void testGetAllStudents() {
        Map<String, Integer> grades = Map.of("Math", 90);
        StudentData student1 = new StudentData("John", "password123", 101, grades);
        StudentData student2 = new StudentData("Jane", "password456", 102, grades);

        when(studentRepository.getAllUsers()).thenReturn(Arrays.asList(student1, student2));

        List<StudentData> allStudents = studentService.getAllStudents();

        assertEquals(2, allStudents.size());
        assertEquals("John", allStudents.get(0).getName());
        assertEquals("Jane", allStudents.get(1).getName());
        verify(studentRepository).getAllUsers();
    }

    @Test
    void testGetGrade() {
        Map<String, Integer> grades = Map.of("Math", 90);
        StudentData student = new StudentData("John", "password123", 101, grades);

        when(studentRepository.getUserByName("John")).thenReturn(student);

        double grade = studentService.getGrade("John", "Math");

        assertEquals(90, grade);
        verify(studentRepository).getUserByName("John");
    }

    @Test
    void testGetStudentCGPA() {
        Map<String, Integer> grades = Map.of("Math", 90, "Science", 80);
        StudentData student = new StudentData("John", "password123", 101, grades);

        when(studentRepository.getUserByName("John")).thenReturn(student);

        double cgpa = studentService.getStudentCGPA("John");

        assertEquals(85.0, cgpa, 0.01);
        verify(studentRepository).getUserByName("John");
    }
}
