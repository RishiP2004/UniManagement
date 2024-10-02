package com.rishi.unimanagement.service;

import com.rishi.unimanagement.data.TAData;
import com.rishi.unimanagement.repo.TARepository;

import java.util.List;
import java.util.stream.Collectors;

public class TAService implements Service {
    private static TAService instance;
    private final TARepository taRepository;

    private TAService() {
        taRepository = TARepository.getInstance();
    }

    public static TAService getInstance() {
        if (instance == null) {
            instance = new TAService();
        }
        return instance;
    }

    public void addTA(TAData ta) {
        taRepository.addUser(ta);
    }

    public TAData getTAByName(String name) {
        return taRepository.getUserByName(name);
    }

    public void updatePassword(String name, String newPassword) {
        TAData ta = taRepository.getUserByName(name);
        if (ta != null) {
            ta.updatePassword(newPassword);
            taRepository.updateUser(ta);
        } else {
            throw new IllegalArgumentException("TA not found");
        }
    }

    public void deleteTA(String name) {
        taRepository.deleteUser(name);
    }

    public List<TAData> getAllTAs() {
        return taRepository.getAllUsers();
    }

    public List<String> getAllTAsMapped() {
        return getAllTAs().stream()
                .map(TAData::getName)
                .collect(Collectors.toList());
    }
}
