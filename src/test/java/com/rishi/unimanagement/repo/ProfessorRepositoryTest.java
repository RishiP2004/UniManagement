package com.rishi.unimanagement.repo;

import com.mongodb.client.FindIterable;
import com.rishi.unimanagement.data.ProfessorData;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfessorRepositoryTest {

    private ProfessorRepository professorRepository;
    private MongoCollection<Document> mockCollection;

    @BeforeEach
    void setUp() {
        MongoDatabase mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);
        when(mockDatabase.getCollection("professors")).thenReturn(mockCollection);
        professorRepository = ProfessorRepository.getInstance();
    }

    @Test
    void addProfessor() {
        ProfessorData professor = new ProfessorData("Prof. Smith", "password");
        professorRepository.addUser(professor);
        verify(mockCollection).insertOne(any(Document.class));
    }

    @Test
    void getProfessorByName() {
        Document doc = new Document("name", "Prof. Smith").append("password", "password");
        when(mockCollection.find(any(Document.class))).thenReturn((FindIterable<Document>) List.of(doc));

        ProfessorData professor = professorRepository.getUserByName("Prof. Smith");
        assertNotNull(professor);
        assertEquals("Prof. Smith", professor.getName());
        verify(mockCollection).find(any(Document.class));
    }

    @Test
    void updateProfessor() {
        ProfessorData professor = new ProfessorData("Prof. Smith", "password");
        professorRepository.updateUser(professor);
        verify(mockCollection).updateOne(any(Document.class), any(Document.class));
    }

    @Test
    void deleteProfessor() {
        professorRepository.deleteUser("Prof. Smith");
        verify(mockCollection).deleteOne(any(Document.class));
    }

    @Test
    void getAllProfessors() {
        Document doc = new Document("name", "Prof. Smith").append("password", "password");
        when(mockCollection.find()).thenReturn((FindIterable<Document>) List.of(doc));

        assertEquals(1, professorRepository.getAllUsers().size());
    }
}
