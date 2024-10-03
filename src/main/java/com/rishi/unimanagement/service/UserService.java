package com.rishi.unimanagement.service;

import com.rishi.unimanagement.data.*;
import com.rishi.unimanagement.repo.AdminRepository;
import com.rishi.unimanagement.repo.StudentRepository;
import com.rishi.unimanagement.repo.TARepository;
import com.rishi.unimanagement.repo.ProfessorRepository;
//Used for general first-time services
public class UserService {
    private static UserService instance;

    private UserService() {}

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User validateLogin(String username, String password) {
        StudentData student = StudentRepository.getInstance().getUserByName(username);
        if (student != null && student.getPassword().equals(password)) {
            return student;
        }
        TAData ta = TARepository.getInstance().getUserByName(username);
        if (ta != null && ta.getPassword().equals(password)) {
            return ta;
        }
        ProfessorData professor = ProfessorRepository.getInstance().getUserByName(username);
        if (professor != null && professor.getPassword().equals(password)) {
            return professor;
        }
        Admin admin = AdminRepository.getInstance().getUserByName(username);

        if (professor != null && professor.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }
}
