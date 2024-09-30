package com.rishi.unimanagement.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.stream.Collectors;
import org.bson.Document;

public final class Database implements DatabaseKeys {
    private static MongoDatabase database;
    private static final List<StudentData> studentData = new ArrayList<>();
    private static final List<TAData> taData = new ArrayList<>();
    private static final List<ProfessorData> profData = new ArrayList<>();

    public Database() {
        connect();
        read();
    }
    
    public static MongoDatabase get() {
        return database;
    }

    private void connect() {
        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase(DATABASE_NAME);
        } catch (Exception e) {
            System.err.println("Connection to database failed: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private void read() {
        if (database != null) {
            readUserData(STUDENT_COLLECTION_NAME, studentData, this::createStudentData);
            readUserData(TA_COLLECTION_NAME, taData, this::createTAData);
            readUserData(PROFESSOR_COLLECTION_NAME, profData, this::createProfessorData);
        } else {
            System.err.println("Database is not initialized.");
        }
    }

    // Updated method to accept specific types
    private <T extends UserData> void readUserData(String collectionName, List<T> userDataList, DocumentCreator<T> creator) {
        MongoCollection<Document> collection = database.getCollection(collectionName);

        for (Document document : collection.find()) {
            T userData = creator.create(document);
            userDataList.add(userData);
        }
    }
    
    private StudentData createStudentData(Document document) {
        String name = document.getString("name");
        String password = document.getString("password");
        int section = document.getInteger("section");
        Map<String, Integer> grades = document.get("grades", Map.class);
        return new StudentData(name, password, section, grades);
    }

    private TAData createTAData(Document document) {
        String name = document.getString("name");
        String password = document.getString("password");
        int section = document.getInteger("section");
        return new TAData(name, password, section);
    }

    private ProfessorData createProfessorData(Document document) {
        return new ProfessorData(document.getString("name"), document.getString("password"));
    }

    public static List<TAData> getAllTAs() {
        return taData;
    }
    
    public static List<String> getAllTAMapped() {
        return taData.stream()
                .map(UserData::getName)
                .collect(Collectors.toList());
    }

    public static UserData getUserData(String name) {
        return Stream.of(studentData, taData, profData)
                .flatMap(List::stream)
                .filter(data -> data.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    
    public static List<String> getStudentsBySection(int section) {
        return studentData.stream()
                .filter(data -> data.getSection() == section)
                .map(UserData::getName)
                .collect(Collectors.toList());
    }
    
    public static List<Integer> getAllGrades() {
        return studentData.stream()
                .flatMap(student -> student.getGrades().values().stream())
                .collect(Collectors.toList());
    }

    public static String getPasswordForUserType(String name) {
        UserData userData = getUserData(name);
        return (userData != null) ? userData.getPassword() : null;
    }

    @FunctionalInterface
    private interface DocumentCreator<T extends UserData> {
        T create(Document document);
    }
}
