package com.rishi.unimanagement.repo;

import com.mongodb.client.FindIterable;
import com.rishi.unimanagement.data.TAData;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TARepositoryTest {
    private TARepository taRepository;
    private MongoCollection<Document> mockCollection;

    @BeforeEach
    void setUp() {
        MongoDatabase mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);
        when(mockDatabase.getCollection("tas")).thenReturn(mockCollection);
        taRepository = TARepository.getInstance();
    }

    @Test
    void addTA() {
        TAData ta = new TAData("Jane", "password", 101);
        taRepository.addUser(ta);
        verify(mockCollection).insertOne(any(Document.class));
    }

    @Test
    void getTAByName() {
        Document doc = new Document("name", "Jane").append("password", "password").append("section", 101);
        when(mockCollection.find(any(Document.class))).thenReturn((FindIterable<Document>) List.of(doc));

        TAData ta = taRepository.getUserByName("Jane");
        assertNotNull(ta);
        assertEquals("Jane", ta.getName());
        verify(mockCollection).find(any(Document.class));
    }

    @Test
    void updateTA() {
        TAData ta = new TAData("Jane", "password", 101);
        taRepository.updateUser(ta);
        verify(mockCollection).updateOne(any(Document.class), any(Document.class));
    }

    @Test
    void deleteTA() {
        taRepository.deleteUser("Jane");
        verify(mockCollection).deleteOne(any(Document.class));
    }

    @Test
    void getAllTAs() {
        Document doc = new Document("name", "Jane").append("password", "password").append("section", 101);
        when(mockCollection.find()).thenReturn((FindIterable<Document>) List.of(doc));

        assertEquals(1, taRepository.getAllUsers().size());
    }
}
