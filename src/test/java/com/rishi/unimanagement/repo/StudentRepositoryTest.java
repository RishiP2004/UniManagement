package com.rishi.unimanagement.repo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.rishi.unimanagement.connection.DatabaseConnectionManager;
import com.rishi.unimanagement.data.StudentData;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.assertions.Assertions.assertNull;
import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentRepositoryTest {

    private static MongoDBContainer mongoDBContainer;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        mongoDBContainer = new MongoDBContainer("mongo:latest");
        mongoDBContainer.start();
        DatabaseConnectionManager.initialize(mongoDBContainer.getReplicaSetUrl());
        database = DatabaseConnectionManager.getConnection();

        studentRepository = StudentRepository.getInstance();
        studentRepository.setCollection(database.getCollection("students"));
    }

    @AfterEach
    void tearDown() {
        database.getCollection("students").drop();
        DatabaseConnectionManager.close();
        mongoDBContainer.stop();
    }

    @Test
    void testAddStudent() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);
        StudentData student = new StudentData("Alice", "password123", 101, grades);

        studentRepository.addUser(student);

        Document foundStudent = database.getCollection("students").find(new Document("name", "Alice")).first();
        assertNotNull(foundStudent);
        assertEquals("Alice", foundStudent.getString("name"));
        assertEquals("password123", foundStudent.getString("password"));
        assertEquals(101, foundStudent.getInteger("section"));
        assertEquals(90, ((Map<String, Integer>) foundStudent.get("grades")).get("Math"));
    }

    @Test
    void testAddMultipleStudents() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);

        for (int i = 0; i < 25000; i++) {
            int section = (i / 50) + 1;
            StudentData student = new StudentData("Student_" + i, "password123", section, grades);
            studentRepository.addUser(student);
        }
        for (int i = 0; i < 25000; i++) {
            Document foundProfessor = database.getCollection("students").find(new Document("name", "Student_" + i)).first();
            assertNotNull(foundProfessor, "Expected student Student_" + i + " to be in the database.");
            assertEquals("Student_" + i, foundProfessor.getString("name"));
            assertEquals("password123", foundProfessor.getString("password"));
        }
    }

    @Test
    void testGetStudentByName() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Science", 85);
        Document studentDoc = new Document("name", "Bob")
                .append("password", "secret456")
                .append("section", 102)
                .append("grades", grades);
        database.getCollection("students").insertOne(studentDoc);

        StudentData retrievedStudent = studentRepository.getUserByName("Bob");

        assertNotNull(retrievedStudent);
        assertEquals("Bob", retrievedStudent.getName());
        assertEquals("secret456", retrievedStudent.getPassword());
        assertEquals(102, retrievedStudent.getSection());
        assertEquals(85, retrievedStudent.getGrade("Science"));
    }

    @Test
    void testGetAllStudents() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);
        studentRepository.addUser(new StudentData("John", "password123", 101, grades));
        studentRepository.addUser(new StudentData("Jane", "password456", 102, grades));

        List<StudentData> allStudents = studentRepository.getAllUsers();
        assertEquals(2, allStudents.size());
    }

    @Test
    void testUpdateStudent() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);
        StudentData student = new StudentData("John", "password123", 101, grades);
        studentRepository.addUser(student);

        student.updatePassword("newpassword");
        studentRepository.updateUser(student);

        StudentData updatedStudent = studentRepository.getUserByName("John");
        assertNotNull(updatedStudent);
        assertEquals("newpassword", updatedStudent.getPassword());
    }

    @Test
    void testDeleteStudent() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);
        StudentData student = new StudentData("John", "password123", 101, grades);
        studentRepository.addUser(student);

        studentRepository.deleteUser("John");

        StudentData deletedStudent = studentRepository.getUserByName("John");
        assertNull(deletedStudent);
    }

    @Test
    void testGetAllGrades() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);
        grades.put("Science", 80);
        studentRepository.addUser(new StudentData("John", "password123", 101, grades));

        List<Integer> allGrades = studentRepository.getAllGrades();
        assertEquals(2, allGrades.size());
        assertTrue(allGrades.contains(90));
        assertTrue(allGrades.contains(80));
    }
}
