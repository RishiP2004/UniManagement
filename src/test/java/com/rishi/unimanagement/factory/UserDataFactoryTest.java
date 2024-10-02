package com.rishi.unimanagement.factory;

import com.rishi.unimanagement.data.*;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class UserDataFactoryTest {

    @Test
    void testCreateStudent() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);

        Document document = new Document("name", "John")
                .append("password", "password")
                .append("section", 101)
                .append("grades", grades);

        User user = UserDataFactory.createUser(document, User.STUDENT);
        assertTrue(user instanceof StudentData);
        assertEquals("John", user.getName());
    }

    @Test
    void testCreateTA() {
        Document document = new Document("name", "TA Jane")
                .append("password", "password")
                .append("section", 102);

        User user = UserDataFactory.createUser(document, User.TA);
        assertTrue(user instanceof TAData);
        assertEquals("TA Jane", user.getName());
    }

    @Test
    void testCreateProfessor() {
        Document document = new Document("name", "Prof. Smith")
                .append("password", "password");

        User user = UserDataFactory.createUser(document, User.PROFESSOR);
        assertTrue(user instanceof ProfessorData);
        assertEquals("Prof. Smith", user.getName());
    }

    @Test
    void testCreateInvalidType() {
        Document document = new Document("name", "Unknown")
                .append("password", "password");

        assertThrows(IllegalArgumentException.class, () -> {
            UserDataFactory.createUser(document, "ets");
        });
    }
}
