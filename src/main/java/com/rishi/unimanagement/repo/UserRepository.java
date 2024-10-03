package com.rishi.unimanagement.repo;

import com.rishi.unimanagement.data.User;
import java.util.List;

public interface UserRepository<T extends User> {
    void addUser(T user);
    T getUserByName(String name);
    void updateUser(T user);
    void deleteUser(String name);
    List<T> getAllUsers();
}
