package com.rishi.unimanagement.repo;

import com.mongodb.client.FindIterable;
import com.rishi.unimanagement.data.StudentData;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentRepositoryTest {

    private StudentRepository studentRepository;
    private MongoCollection<Document> mockCollection;

    @BeforeEach
    void setUp() {
        MongoDatabase mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);
        when(mockDatabase.getCollection("students")).thenReturn(mockCollection);
        studentRepository =  StudentRepository.getInstance();
    }

    @Test
    void addStudent() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);
        StudentData student = new StudentData("John", "1234", 101, grades);
        studentRepository.addUser(student);
        verify(mockCollection).insertOne(any(Document.class));
    }

    @Test
    void getStudentByName() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);
        Document doc = new Document("name", "John").append("password", "1234").append("section", 101).append("grades", grades);
        when(mockCollection.find(any(Document.class))).thenReturn((FindIterable<Document>) List.of(doc));

        StudentData student = studentRepository.getUserByName("John");
        assertNotNull(student);
        assertEquals("John", student.getName());
        verify(mockCollection).find(any(Document.class));
    }

    @Test
    void updateStudent() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);
        StudentData student = new StudentData("John", "1234", 101, grades);
        studentRepository.updateUser(student);
        verify(mockCollection).updateOne(any(Document.class), any(Document.class));
    }

    @Test
    void deleteStudent() {
        studentRepository.deleteUser("John");
        verify(mockCollection).deleteOne(any(Document.class));
    }

    @Test
    void getAllStudents() {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 90);
        Document doc = new Document("name", "John").append("password", "1234").append("section", 101).append("grades", grades);
        when(mockCollection.find()).thenReturn((FindIterable<Document>) List.of(doc));

        assertEquals(1, studentRepository.getAllUsers().size());
    }
}
