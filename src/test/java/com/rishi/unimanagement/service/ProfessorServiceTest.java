package com.rishi.unimanagement.service;

import com.rishi.unimanagement.data.ProfessorData;
import com.rishi.unimanagement.repo.ProfessorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfessorServiceTest {
    private ProfessorService professorService;
    private ProfessorRepository professorRepository;

    @BeforeEach
    void setUp() {
        professorRepository = mock(ProfessorRepository.class);

        professorService = ProfessorService.getInstance();
        professorService.setRepository(professorRepository);
    }

    @Test
    void testAddProfessor() {
        ProfessorData professor = new ProfessorData("Dr. Smith", "password123");

        professorService.addProfessor(professor);
        verify(professorRepository).addUser(professor);
    }

    @Test
    void testGetProfessorByName() {
        ProfessorData professor = new ProfessorData("Dr. Smith", "password123");

        when(professorRepository.getUserByName("Dr. Smith")).thenReturn(professor);

        ProfessorData retrievedProfessor = professorService.getProfessorByName("Dr. Smith");

        assertNotNull(retrievedProfessor);
        assertEquals("Dr. Smith", retrievedProfessor.getName());
        assertEquals("password123", retrievedProfessor.getPassword());
        verify(professorRepository).getUserByName("Dr. Smith");
    }

    @Test
    void testUpdatePassword_Success() {
        ProfessorData professor = new ProfessorData("Dr. Smith", "oldPassword");

        when(professorRepository.getUserByName("Dr. Smith")).thenReturn(professor);

        professorService.updatePassword("Dr. Smith", "newPassword");

        assertEquals("newPassword", professor.getPassword());
        verify(professorRepository).updateUser(professor);
    }

    @Test
    void testUpdatePassword_Failure() {
        when(professorRepository.getUserByName("Unknown")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> professorService.updatePassword("Unknown", "newPassword"));
    }

    @Test
    void testDeleteProfessor() {
        professorService.deleteProfessor("Dr. Smith");
        verify(professorRepository).deleteUser("Dr. Smith");
    }

    @Test
    void testGetAllProfessors() {
        ProfessorData professor1 = new ProfessorData("Dr. Smith", "password123");
        ProfessorData professor2 = new ProfessorData("Dr. Johnson", "password456");
        when(professorRepository.getAllUsers()).thenReturn(Arrays.asList(professor1, professor2));

        List<ProfessorData> allProfessors = professorService.getAllProfessors();

        assertEquals(2, allProfessors.size());
        assertEquals("Dr. Smith", allProfessors.get(0).getName());
        assertEquals("Dr. Johnson", allProfessors.get(1).getName());
        verify(professorRepository).getAllUsers();
    }
}
