package com.rishi.unimanagement.service;

import com.rishi.unimanagement.data.ProfessorData;
import com.rishi.unimanagement.repo.ProfessorRepository;

import java.util.List;

public class ProfessorService implements Service {

    private static ProfessorService instance;
    private final ProfessorRepository professorRepository;

    private ProfessorService() {
        professorRepository = ProfessorRepository.getInstance();
    }

    public static ProfessorService getInstance() {
        if (instance == null) {
            instance = new ProfessorService();
        }
        return instance;
    }
    public void addProfessor(ProfessorData professor) {
        professorRepository.addUser(professor);
    }

    public ProfessorData getProfessorByName(String name) {
        return professorRepository.getUserByName(name);
    }

    public void updatePassword(String name, String newPassword) {
        ProfessorData professor = professorRepository.getUserByName(name);

        if (professor != null) {
            professor.updatePassword(newPassword);
            professorRepository.updateUser(professor);
        } else {
            throw new IllegalArgumentException("Professor not found");
        }
    }

    public void deleteProfessor(String name) {
        professorRepository.deleteUser(name);
    }

    public List<ProfessorData> getAllProfessors() {
        return professorRepository.getAllUsers();
    }
}
