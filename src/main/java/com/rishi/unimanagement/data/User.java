package com.rishi.unimanagement.data;

import org.bson.Document;

public interface User {
    String STUDENT = "student";
    String TA = "ta";
    String PROFESSOR = "professor";
    String ADMIN = "admin";

    String getName();
    String getPassword();
    String getType();
    void updatePassword(String newPassword);
    Document toDocument();
}
