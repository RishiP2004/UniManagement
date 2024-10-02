package com.rishi.unimanagement.service;

import com.rishi.unimanagement.data.TAData;
import com.rishi.unimanagement.repo.TARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TAServiceTest {

    private TAService taService;
    private TARepository taRepository;

    @BeforeEach
    void setUp() {
        taRepository = mock(TARepository.class);

        taService = TAService.getInstance();
        taService.setTaRepository(taRepository);
    }

    @Test
    void testAddTA() {
        TAData ta = new TAData("John", "password123", 101);

        taService.addTA(ta);
        verify(taRepository).addUser(ta);
    }

    @Test
    void testGetTAByName() {
        TAData ta = new TAData("John", "password123", 101);

        when(taRepository.getUserByName("John")).thenReturn(ta);

        TAData retrievedTA = taService.getTAByName("John");

        assertNotNull(retrievedTA);
        assertEquals("John", retrievedTA.getName());
        assertEquals("password123", retrievedTA.getPassword());
        verify(taRepository).getUserByName("John");
    }

    @Test
    void testUpdatePassword_Success() {
        TAData ta = new TAData("John", "oldPassword", 101);

        when(taRepository.getUserByName("John")).thenReturn(ta);
        taService.updatePassword("John", "newPassword");
        assertEquals("newPassword", ta.getPassword());
        verify(taRepository).updateUser(ta);
    }

    @Test
    void testUpdatePassword_Failure() {
        when(taRepository.getUserByName("Unknown")).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> taService.updatePassword("Unknown", "newPassword"));
        verify(taRepository).getUserByName("Unknown");
    }

    @Test
    void testDeleteTA() {
        taService.deleteTA("John");
        verify(taRepository).deleteUser("John");
    }

    @Test
    void testGetAllTAs() {
        TAData ta1 = new TAData("John", "password123", 101);
        TAData ta2 = new TAData("Jane", "password456", 102);

        when(taRepository.getAllUsers()).thenReturn(Arrays.asList(ta1, ta2));

        List<TAData> allTAs = taService.getAllTAs();

        assertEquals(2, allTAs.size());
        assertEquals("John", allTAs.get(0).getName());
        assertEquals("Jane", allTAs.get(1).getName());
        verify(taRepository).getAllUsers();
    }

    @Test
    void testGetAllTAsMapped() {
        TAData ta1 = new TAData("John", "password123", 101);
        TAData ta2 = new TAData("Jane", "password456", 102);

        when(taRepository.getAllUsers()).thenReturn(Arrays.asList(ta1, ta2));

        List<String> taNames = taService.getAllTAsMapped();

        assertEquals(2, taNames.size());
        assertTrue(taNames.contains("John"));
        assertTrue(taNames.contains("Jane"));
        verify(taRepository).getAllUsers();
    }
}
