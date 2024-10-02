package com.rishi.unimanagement.service;

import com.rishi.unimanagement.data.TAData;
import com.rishi.unimanagement.repo.TARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;

class TAServiceTest {

    private TAService taService;
    private TARepository taRepository;

    @BeforeEach
    void setUp() {
        taRepository = mock(TARepository.class);
        taService = TAService.getInstance();
    }

    @Test
    void testAddTA() {
        TAData ta = new TAData("John", "password", 101);
        taService.addTA(ta);
        verify(taRepository).addUser(ta);
    }

    @Test
    void testGetTAByName() {
        TAData ta = new TAData("John", "password", 101);
        when(taRepository.getUserByName("John")).thenReturn(ta);

        TAData result = taService.getTAByName("John");
        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(taRepository).getUserByName("John");
    }

    @Test
    void testUpdateTAPassword() {
        TAData ta = new TAData("John", "password", 101);
        when(taRepository.getUserByName("John")).thenReturn(ta);

        taService.updatePassword("John", "newPassword");
        verify(ta).updatePassword("newPassword");
        verify(taRepository).updateUser(ta);
    }

    @Test
    void testDeleteTA() {
        taService.deleteTA("John");
        verify(taRepository).deleteUser("John");
    }

    @Test
    void testGetAllTAs() {
        List<TAData> tas = new ArrayList<>();
        tas.add(new TAData("John", "password", 101));
        when(taRepository.getAllUsers()).thenReturn(tas);

        List<TAData> result = taService.getAllTAs();
        assertEquals(1, result.size());
        verify(taRepository).getAllUsers();
    }
}
