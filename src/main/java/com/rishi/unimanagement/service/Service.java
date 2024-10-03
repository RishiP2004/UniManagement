package com.rishi.unimanagement.service;

import com.rishi.unimanagement.repo.UserRepository;

public interface Service {
    void setRepository(UserRepository<?> repository);
    void updatePassword(String name, String newPassword);
}
