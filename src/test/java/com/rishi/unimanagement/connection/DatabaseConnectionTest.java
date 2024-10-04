package com.rishi.unimanagement.connection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    private MongoDatabase database;

    @BeforeEach
    void setUp() {
        DatabaseConnectionManager.initialize(null);
        database = DatabaseConnectionManager.getConnection();
    }

    @AfterEach
    void tearDown() {
        DatabaseConnectionManager.close();
    }

    @Test
    void testRealDatabaseConnection() {
        assertNotNull(database);
        assertEquals("UniManagement", database.getName());
    }
}
