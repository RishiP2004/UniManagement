package com.rishi.unimanagement.repo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.rishi.unimanagement.data.TAData;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TARepositoryTest {

    private static MongoDBContainer mongoDBContainer;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private TARepository taRepository;

    @BeforeEach
    void setUp() {
        mongoDBContainer = new MongoDBContainer("mongo:latest");
        mongoDBContainer.start();

        mongoClient = MongoClients.create(mongoDBContainer.getConnectionString());
        database = mongoClient.getDatabase("test");

        taRepository = TARepository.getInstance();
        taRepository.setCollection(database.getCollection("ta"));
    }

    @AfterEach
    void tearDown() {
        database.getCollection("ta").drop();
        mongoClient.close();
        mongoDBContainer.stop();
    }

    @Test
    void testAddTA() {
        TAData ta = new TAData("Alice", "password123", 101);

        taRepository.addUser(ta);

        Document foundTA = database.getCollection("ta").find(new Document("name", "Alice")).first();
        assertNotNull(foundTA);
        assertEquals("Alice", foundTA.getString("name"));
        assertEquals("password123", foundTA.getString("password"));
        assertEquals(101, foundTA.getInteger("section"));
    }

    @Test
    void testGetTAByName() {
        Document taDoc = new Document("name", "Bob")
                .append("password", "secret456")
                .append("section", 102);
        database.getCollection("ta").insertOne(taDoc);

        TAData retrievedTA = taRepository.getUserByName("Bob");

        assertNotNull(retrievedTA);
        assertEquals("Bob", retrievedTA.getName());
        assertEquals("secret456", retrievedTA.getPassword());
        assertEquals(102, retrievedTA.getSection());
    }

    @Test
    void testGetAllTAs() {
        taRepository.addUser(new TAData("John", "password123", 101));
        taRepository.addUser(new TAData("Jane", "password456", 102));

        List<TAData> allTAs = taRepository.getAllUsers();
        assertEquals(2, allTAs.size());
    }

    @Test
    void testUpdateTA() {
        TAData ta = new TAData("John", "password123", 101);
        taRepository.addUser(ta);

        ta.updatePassword("testpass");
        taRepository.updateUser(ta);

        TAData updatedTA = taRepository.getUserByName("John");
        assertNotNull(updatedTA);
        assertEquals("testpass", updatedTA.getPassword());
    }

    @Test
    void testDeleteTA() {
        TAData ta = new TAData("John", "password123", 101);
        taRepository.addUser(ta);

        taRepository.deleteUser("John");

        TAData deletedTA = taRepository.getUserByName("John");
        assertNull(deletedTA);
    }
}
