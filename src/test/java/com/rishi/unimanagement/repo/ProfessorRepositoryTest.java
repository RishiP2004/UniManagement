package com.rishi.unimanagement.repo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.rishi.unimanagement.data.ProfessorData;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProfessorRepositoryTest {

    private static MongoDBContainer mongoDBContainer;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private ProfessorRepository professorRepository;

    @BeforeEach
    void setUp() {
        mongoDBContainer = new MongoDBContainer("mongo:latest");
        mongoDBContainer.start();

        mongoClient = MongoClients.create(mongoDBContainer.getConnectionString());
        database = mongoClient.getDatabase("test");

        professorRepository = ProfessorRepository.getInstance();
        professorRepository.setCollection(database.getCollection("professors"));
    }

    @AfterEach
    void tearDown() {
        database.getCollection("professors").drop();
        mongoClient.close();
        mongoDBContainer.stop();
    }

    @Test
    void testAddProfessor() {
        ProfessorData prof = new ProfessorData("Maje", "password123");

        professorRepository.addUser(prof);

        Document foundTA = database.getCollection("professors").find(new Document("name", "Maje")).first();
        assertNotNull(foundTA);
        assertEquals("Maje", foundTA.getString("name"));
        assertEquals("password123", foundTA.getString("password"));
    }

    @Test
    void testGetProfessorByName() {
        Document taDoc = new Document("name", "Lin")
                .append("password", "secret456");
        database.getCollection("professors").insertOne(taDoc);

        ProfessorData retrievedProf = professorRepository.getUserByName("Lin");

        assertNotNull(retrievedProf);
        assertEquals("Lin", retrievedProf.getName());
        assertEquals("secret456", retrievedProf.getPassword());
    }

    @Test
    void testGetAllProfessors() {
        professorRepository.addUser(new ProfessorData("John", "password123"));
        professorRepository.addUser(new ProfessorData("Jane", "password456"));

        List<ProfessorData> allTAs = professorRepository.getAllUsers();
        assertEquals(2, allTAs.size());
    }

    @Test
    void testUpdateProfessor() {
        ProfessorData prof = new ProfessorData("John", "password123");
        professorRepository.addUser(prof);

        prof.updatePassword("testpass");
        professorRepository.updateUser(prof);

        ProfessorData updatedProf = professorRepository.getUserByName("John");
        assertNotNull(updatedProf);
        assertEquals("testpass", updatedProf.getPassword());
    }

    @Test
    void testDeleteTA() {
        ProfessorData ta = new ProfessorData("John", "password123");
        professorRepository.addUser(ta);

        professorRepository.deleteUser("John");

        ProfessorData deletedTA = professorRepository.getUserByName("John");
        assertNull(deletedTA);
    }
}
