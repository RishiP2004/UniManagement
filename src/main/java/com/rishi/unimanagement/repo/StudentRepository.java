package com.rishi.unimanagement.repo;

import com.rishi.unimanagement.data.StudentData;
import com.rishi.unimanagement.connection.DatabaseConnectionManager;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentRepository implements UserRepository<StudentData> {

    private MongoCollection<Document> studentCollection;
    private static StudentRepository instance;

    private StudentRepository() {
        this.studentCollection = DatabaseConnectionManager.getConnection().getCollection("students");
    }

    public void setCollection(MongoCollection<Document> collection) {
        studentCollection = collection;
    }

    public static StudentRepository getInstance() {
        if (instance == null) {
            instance = new StudentRepository();
        }
        return instance;
    }

    @Override
    public void addUser(StudentData student) {
        studentCollection.insertOne(student.toDocument());
    }

    @Override
    public StudentData getUserByName(String name) {
        Document doc = studentCollection.find(new Document("name", name)).first();
        if (doc != null) {
            return new StudentData(doc.getString("name"), doc.getString("password"),
                    doc.getInteger("section"), doc.get("grades", Map.class));
        }
        return null;
    }

    @Override
    public void updateUser(StudentData student) {
        Document filter = new Document("name", student.getName());
        Document update = new Document("$set", student.toDocument());
        studentCollection.updateOne(filter, update);
    }

    @Override
    public void deleteUser(String name) {
        studentCollection.deleteOne(new Document("name", name));
    }

    @Override
    public List<StudentData> getAllUsers() {
        List<StudentData> students = new ArrayList<>();
        for (Document doc : studentCollection.find()) {
            students.add(new StudentData(doc.getString("name"), doc.getString("password"),
                    doc.getInteger("section"), doc.get("grades", Map.class)));
        }
        return students;
    }
    
    public List<Integer> getAllGrades() {
        List<Integer> allGrades = new ArrayList<>();

        for (Document doc : studentCollection.find()) {
            Map<String, Integer> grades = doc.get("grades", Map.class);

            if (grades != null) {
                allGrades.addAll(grades.values());
            }
        }

        return allGrades;
    }

}
