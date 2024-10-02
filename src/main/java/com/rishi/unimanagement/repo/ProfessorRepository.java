package com.rishi.unimanagement.repo;

import com.rishi.unimanagement.data.ProfessorData;
import com.rishi.unimanagement.connection.DatabaseConnectionManager;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ProfessorRepository implements UserRepository<ProfessorData> {

    private final MongoCollection<Document> professorCollection;
    private static ProfessorRepository instance;

    private ProfessorRepository() {
        this.professorCollection = DatabaseConnectionManager.getConnection().getCollection("professor");
    }

    public static ProfessorRepository getInstance() {
        if (instance == null) {
            instance = new ProfessorRepository(); 
        }
        return instance;
    }
    @Override
    public void addUser(ProfessorData professor) {
        professorCollection.insertOne(professor.toDocument());
    }

    @Override
    public ProfessorData getUserByName(String name) {
        Document doc = professorCollection.find(new Document("name", name)).first();
        if (doc != null) {
            return new ProfessorData(doc.getString("name"), doc.getString("password"));
        }
        return null;
    }

    @Override
    public void updateUser(ProfessorData professor) {
        Document filter = new Document("name", professor.getName());
        Document update = new Document("$set", professor.toDocument());
        professorCollection.updateOne(filter, update);
    }

    @Override
    public void deleteUser(String name) {
        professorCollection.deleteOne(new Document("name", name));
    }

    @Override
    public List<ProfessorData> getAllUsers() {
        List<ProfessorData> professors = new ArrayList<>();
        for (Document doc : professorCollection.find()) {
            professors.add(new ProfessorData(doc.getString("name"), doc.getString("password")));
        }
        return professors;
    }
}
