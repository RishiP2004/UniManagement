package com.rishi.unimanagement.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            database = mongoClient.getDatabase(DATABASE_NAME);
        } catch (Exception e) {
            System.exit(0);
        }
    }
    
    private void read() {
        try {
            if (database != null) {
                readUserData(STUDENT_COLLECTION_NAME, studentData, document -> {
                    String name = document.getString("name");
                    String password = document.getString("password");
                    int section = document.getInteger("section");
                    Map<String, Integer> grades = document.get("grades", Map.class);
                    return new StudentData(name, password, section, grades);
                });

                readUserData(TA_COLLECTION_NAME, taData, document -> {
                    String name = document.getString("name");
                    String password = document.getString("password");
                    int section = document.getInteger("section");
                    return new TAData(name, password, section);
                });

                readUserData(PROFESSOR_COLLECTION_NAME, profData, document -> 
                        new ProfessorData(document.getString("name"), document.getString("password")));
            }
        } catch (Exception e) {
        }
    }

    private void readUserData(String collectionName, List<? extends UserData> userDataList, DocumentCreator creator) {
        MongoCollection<Document> collection = database.getCollection(collectionName);

        for (Document document : collection.find()) {
            UserData userData = creator.create(document);
            userDataList.add(userData);
        }
    }
    
    public static List<TAData> getAllTAs() {
        return taData;
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
    
    public static String getPasswordForUserType(String name) {
        UserData userData = getUserData(name);
        return (userData != null) ? userData.getPassword() : null;
    }

    @FunctionalInterface
    private interface DocumentCreator {
        UserData create(Document document);
    }
}
