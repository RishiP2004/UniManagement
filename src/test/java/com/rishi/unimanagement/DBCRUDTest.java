package com.rishi.unimanagement;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.rishi.unimanagement.data.Database;
import com.rishi.unimanagement.data.StudentData;
import com.rishi.unimanagement.data.ProfessorData;
import com.rishi.unimanagement.data.TAData;
import com.rishi.unimanagement.data.UserData;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DBCRUDTest {
    private MongoDatabase mockMongoDatabase;
    private MongoCollection<Document> mockCollection;
    private Database database;

    public void setUp() {
        mockMongoDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class); 

        when(mockMongoDatabase.getCollection(anyString())).thenReturn(mockCollection);

        database = new Database();  
        database.set(mockMongoDatabase);
    }

    private void mockCollectionFind(List<Document> documents) {
        when(mockCollection.find()).thenReturn((FindIterable<Document>) documents);
        when(mockMongoDatabase.getCollection(anyString())).thenReturn(mockCollection);
    }

    // Test CREATE operations for TA, Student, and Professor
    @Test
    public void testCreateTA() {
        Document newTADoc = new Document("name", "New TA")
                .append("password", "NewTAPassword")
                .append("section", 101);

        database.regTA("New TA", "NewTAPassword", 101);
        
        verify(mockCollection, times(1)).insertOne(newTADoc);
    }

    @Test
    public void testCreateStudent() {
        Map<String, Integer> grades = Map.of("Math", 90, "Science", 85, "History", 88);
        Document newStudentDoc = new Document("name", "New Student")
                .append("password", "NewStudentPassword")
                .append("section", 101)
                .append("grades", grades);

        database.regStudent("New Student", "NewStudentPassword", 101, grades);

        verify(mockCollection, times(1)).insertOne(newStudentDoc);
    }

    @Test
    public void testCreateProfessor() {
        Document newProfDoc = new Document("name", "New Professor")
                .append("password", "NewProfPassword");

        database.regProf("New Professor", "NewProfPassword", 0);

        verify(mockCollection, times(1)).insertOne(newProfDoc);
    }

    // Test READ operations for TA, Student, and Professor
    @Test
    public void testReadTAs() {
        Document taDoc = new Document("name", "TAName")
                .append("password", "TAPassword")
                .append("section", 101);

        mockCollectionFind(List.of(taDoc));
        database.read();

        List<TAData> taList = Database.getAllTAs();
        assertEquals(1, taList.size());
        assertEquals("TAName", taList.get(0).getName());
    }

    @Test
    public void testReadStudents() {
        Map<String, Integer> grades = Map.of("Math", 90, "Science", 85, "History", 88);
        Document studentDoc = new Document("name", "StudentName")
                .append("password", "StudentPassword")
                .append("section", 101)
                .append("grades", grades);

        mockCollectionFind(List.of(studentDoc));
        database.read();

        List<StudentData> studentList = Database.getAllStudents();
        assertEquals(1, studentList.size());
        assertEquals("StudentName", studentList.get(0).getName());
    }

    @Test
    public void testReadProfessors() {
        Document profDoc = new Document("name", "ProfName")
                .append("password", "ProfPassword");

        mockCollectionFind(List.of(profDoc));
        database.read();

        List<ProfessorData> profList = Database.getAllProfessors();
        assertEquals(1, profList.size());
        assertEquals("ProfName", profList.get(0).getName());
    }

    // Test UPDATE operation for Student's password
    @Test
    public void testUpdateStudentPassword() {
        Document studentDoc = new Document("name", "StudentName")
                .append("password", "OldPassword")
                .append("section", 101);

        mockCollectionFind(List.of(studentDoc));
        database.read();
        
        database.getUserData("StudentName").setPassword("NewPassword");

        Document updatedDoc = new Document("name", "StudentName")
                .append("password", "NewPassword")
                .append("section", 101);

        verify(mockCollection, times(1)).replaceOne(eq(studentDoc), eq(updatedDoc));
    }

    // Test DELETE operation for TA
    @Test
    public void testDeleteTA() {
        Document taDoc = new Document("name", "TAName")
                .append("password", "TAPassword")
                .append("section", 101);

        mockCollectionFind(List.of(taDoc));
        database.read();

        database.getUserData("TAName").delete();

        verify(mockCollection, times(1)).deleteOne(taDoc);
    }

    // Test for retrieving a non-existent user
    @Test
    public void testGetUserByNonExistentName() {
            // Mocking FindIterable<Document> to return an empty result
        FindIterable<Document> mockFindIterable = mock(FindIterable.class);

        // Configure the mock to simulate no results
        when(mockFindIterable.iterator()).thenReturn(mock(MongoCursor.class));  // Empty iterator
        when(mockCollection.find()).thenReturn(mockFindIterable);
        when(mockMongoDatabase.getCollection(anyString())).thenReturn(mockCollection);

        // Simulate reading the database
        database.read();

        // Retrieve a user that doesn't exist
        UserData user = Database.getUserData("NonExistentUser");

        // Assert that the returned user is null, as no user should be found
        assertNull(user, "Expected null for a non-existent user.");
    }

    // Test CREATE for Student with Invalid Section Type
    @Test
    public void testCreateStudentWithInvalidSectionType() {
        Document studentDoc = new Document("name", "StudentInvalidSection")
                .append("password", "password")
                .append("section", "invalidSectionType");  // Invalid section type

        assertThrows(ClassCastException.class, () -> {
            database.createStudentData(studentDoc);
        });
    }

    // Test CREATE for TA with Duplicate Username
    @Test
    public void testCreateTAWithDuplicateUsername() {
        Document taDoc = new Document("name", "ExistingTA")
                .append("password", "TAPassword")
                .append("section", 101);

        mockCollectionFind(List.of(taDoc));
        database.read();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            database.regTA("ExistingTA", "NewPassword", 102);
        });

        assertEquals("Username must be unique", exception.getMessage());
    }

    // Test simultaneous insertions
    @Test
    public void testSimultaneousInsertions() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            database.regTA("ConcurrentTA1", "Password1", 101);
        });
        Thread thread2 = new Thread(() -> {
            database.regTA("ConcurrentTA2", "Password2", 102);
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        verify(mockCollection, times(1)).insertOne(new Document("name", "ConcurrentTA1")
                .append("password", "Password1")
                .append("section", 101));
        verify(mockCollection, times(1)).insertOne(new Document("name", "ConcurrentTA2")
                .append("password", "Password2")
                .append("section", 102));
    }
    
    @Test
    public void testDatabaseConnectionFailure() {
        // Simulate the database being unavailable by throwing an exception on collection retrieval
        when(mockMongoDatabase.getCollection(anyString())).thenThrow(new RuntimeException("Database connection error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            database.read();  // Attempt to read data while the connection is down
        });

        // Ensure that the thrown exception matches the expected error message
        assertEquals("Database connection error", exception.getMessage());
    }
    /**
    @Test
    public void testInvalidCollectionName() {
        // Simulate retrieving from a non-existent collection
        when(mockMongoDatabase.getCollection("InvalidCollection")).thenReturn(null);

        // Verify that an IllegalArgumentException is thrown when an invalid collection name is used
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            database.readFromCollection("InvalidCollection");
        });

        // Ensure that the correct error message is provided
        assertEquals("Collection does not exist", exception.getMessage());
    }*/

    @Test
    public void testDeleteTAWithAssignedStudents() {
        // Mock a TA and students in the same section
        Document taDoc = new Document("name", "TAWithStudents")
                .append("password", "TAPassword")
                .append("section", 101);
        Document studentDoc = new Document("name", "Student1")
                .append("password", "StudentPassword")
                .append("section", 101);

        // Mock the TA and student documents in the collection
        mockCollectionFind(List.of(taDoc, studentDoc));
        database.read();

        // Attempt to delete the TA who is assigned students, which should trigger an exception
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            database.getUserData("TAWithStudents").delete();
        });

        // Ensure the correct error message is provided
        assertEquals("Cannot delete TA with assigned students", exception.getMessage());
    }

    @Test
    public void testLargeDataInsertion() {
        // Simulate the insertion of 1000 students
        List<StudentData> largeStudentList = new ArrayList<>();
        Map<String, Integer> grades = Map.of("Math", 90, "Science", 85, "History", 88);

        for (int i = 0; i < 1000; i++) {
            largeStudentList.add(new StudentData("Student" + i, "Password" + i, 101, grades));
        }

        // Insert each student into the database
        for (StudentData student : largeStudentList) {
            database.regStudent(student.getName(), student.getPassword(), student.getSection(), student.getGrades());
        }

        // Verify that the insert operation is called 1000 times
        verify(mockCollection, times(1000)).insertOne(any(Document.class));
    }

    @Test
    public void testBulkDeleteStudents() {
        // Mock a large number of student documents in the database
        List<Document> studentDocs = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            studentDocs.add(new Document("name", "Student" + i)
                    .append("password", "pass" + i)
                    .append("section", 101));
        }

        // Mock the retrieval of the students from the database
        mockCollectionFind(studentDocs);
        database.read();

        // Simulate bulk deletion of all 1000 students
        for (int i = 0; i < 1000; i++) {
            database.getUserData("Student" + i).delete();
        }

        // Verify that the delete operation is called 1000 times
        verify(mockCollection, times(1000)).deleteOne(any(Document.class));
    }

    @Test
    public void testProfessorQueryingStudents() {
        // Mock a professor and two students in the same section
        Document profDoc = new Document("name", "ProfName")
                .append("password", "ProfPassword");
        Document studentDoc1 = new Document("name", "Student1")
                .append("password", "pass1")
                .append("section", 101);
        Document studentDoc2 = new Document("name", "Student2")
                .append("password", "pass2")
                .append("section", 101);

        // Mock the retrieval of the professor and students from the database
        mockCollectionFind(List.of(profDoc, studentDoc1, studentDoc2));
        database.read();

        // Simulate the professor querying all students in section 101
        List<String> students = database.getStudentsBySection(101);

        // Verify that the correct students are returned
        assertEquals(2, students.size());
        assertTrue(students.contains("Student1"));
        assertTrue(students.contains("Student2"));
    }

    
}
