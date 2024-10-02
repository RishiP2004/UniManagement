package com.rishi.unimanagement.connection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    private MongoClient mongoClient;
    private MongoDatabase database;

    @BeforeEach
    void setUp() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("UniManagement");
    }

    @AfterEach
    void tearDown() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    @Test
    void testRealDatabaseConnection() {
        assertNotNull(database);
        assertEquals("UniManagement", database.getName());
    }
}
