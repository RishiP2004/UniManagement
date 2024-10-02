package com.rishi.unimanagement.service;

import com.rishi.unimanagement.data.ProfessorData;
import com.rishi.unimanagement.repo.ProfessorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;

class ProfessorServiceTest {

    private ProfessorService professorService;
    private ProfessorRepository professorRepository;

    @BeforeEach
    void setUp() {
        professorRepository = mock(ProfessorRepository.class);
        professorService = ProfessorService.getInstance();
    }

    @Test
    void testAddProfessor() {
        ProfessorData professor = new ProfessorData("Dr. Smith", "password");
        professorService.addProfessor(professor);
        verify(professorRepository).addUser(professor);
    }

    @Test
    void testGetProfessorByName() {
        ProfessorData professor = new ProfessorData("Dr. Smith", "password");
        when(professorRepository.getUserByName("Dr. Smith")).thenReturn(professor);

        ProfessorData result = professorService.getProfessorByName("Dr. Smith");
        assertNotNull(result);
        assertEquals("Dr. Smith", result.getName());
        verify(professorRepository).getUserByName("Dr. Smith");
    }

    @Test
    void testUpdateProfessorPassword() {
        ProfessorData professor = new ProfessorData("Dr. Smith", "password");
        when(professorRepository.getUserByName("Dr. Smith")).thenReturn(professor);

        professorService.updatePassword("Dr. Smith", "newPassword");
        verify(professor).updatePassword("newPassword");
        verify(professorRepository).updateUser(professor);
    }

    @Test
    void testDeleteProfessor() {
        professorService.deleteProfessor("Dr. Smith");
        verify(professorRepository).deleteUser("Dr. Smith");
    }

    @Test
    void testGetAllProfessors() {
        List<ProfessorData> professors = new ArrayList<>();
        professors.add(new ProfessorData("Dr. Smith", "password"));
        when(professorRepository.getAllUsers()).thenReturn(professors);

        List<ProfessorData> result = professorService.getAllProfessors();
        assertEquals(1, result.size());
        verify(professorRepository).getAllUsers();
    }
}
