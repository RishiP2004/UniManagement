package com.rishi.unimanagement.service;

import com.rishi.unimanagement.data.StudentData;
import com.rishi.unimanagement.repo.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

public class StudentService implements Service {

    private static StudentService instance;
    private final StudentRepository studentRepository;

    private StudentService() {
        studentRepository = StudentRepository.getInstance();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public void addStudent(StudentData student) {
        studentRepository.addUser(student);
    }

    public StudentData getStudentByName(String name) {
        return studentRepository.getUserByName(name);
    }

    public void updatePassword(String name, String newPassword) {
        StudentData student = studentRepository.getUserByName(name);
        if (student != null) {
            student.updatePassword(newPassword);
            studentRepository.updateUser(student);
        } else {
            throw new IllegalArgumentException("Student not found");
        }
    }

    public List<String> getStudentsBySection(int section) {
        return this.getAllStudents().stream()
                .filter(data -> data.getSection() == section)
                .map(StudentData::getName)
                .collect(Collectors.toList());
    }

    public void updateGrade(String name, String subject, int newGrade) {
        StudentData student = studentRepository.getUserByName(name);
        if (student != null) {
            student.setGrade(subject, newGrade);
            studentRepository.updateUser(student);
        } else {
            throw new IllegalArgumentException("Student not found");
        }
    }

    public void deleteStudent(String name) {
        studentRepository.deleteUser(name);
    }

    public List<StudentData> getAllStudents() {
        return studentRepository.getAllUsers();
    }

    public double getGrade(String name, String subject) {
        StudentData student = studentRepository.getUserByName(name);
        if (student != null) {
            return student.getGrade(subject);
        }
        throw new IllegalArgumentException("Student not found");
    }

    public double getStudentCGPA(String name) {
        StudentData student = studentRepository.getUserByName(name);
        if (student != null) {
            return student.getCGPA();
        }
        throw new IllegalArgumentException("Student not found");
    }
}
