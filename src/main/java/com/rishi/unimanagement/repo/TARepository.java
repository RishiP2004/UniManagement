package com.rishi.unimanagement.repo;

import com.rishi.unimanagement.data.TAData;
import com.rishi.unimanagement.connection.DatabaseConnectionManager;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class TARepository implements UserRepository<TAData> {

    private MongoCollection<Document> taCollection;
    private static TARepository instance;

    private TARepository() {
        this.taCollection = DatabaseConnectionManager.getConnection().getCollection("ta");
    }

    public static TARepository getInstance() {
        if (instance == null) {
            instance = new TARepository(); 
        }
        return instance;
    }

    public void setCollection(MongoCollection<Document> collection) {
        taCollection = collection;
    }

    @Override
    public void addUser(TAData ta) {
        taCollection.insertOne(ta.toDocument());
    }

    @Override
    public TAData getUserByName(String name) {
        Document doc = taCollection.find(new Document("name", name)).first();
        if (doc != null) {
            return new TAData(doc.getString("name"), doc.getString("password"), doc.getInteger("section"));
        }
        return null;
    }

    @Override
    public void updateUser(TAData ta) {
        Document filter = new Document("name", ta.getName());
        Document update = new Document("$set", ta.toDocument());
        taCollection.updateOne(filter, update);
    }

    @Override
    public void deleteUser(String name) {
        taCollection.deleteOne(new Document("name", name));
    }

    @Override
    public List<TAData> getAllUsers() {
        List<TAData> tas = new ArrayList<>();
        for (Document doc : taCollection.find()) {
            tas.add(new TAData(doc.getString("name"), doc.getString("password"), doc.getInteger("section")));
        }
        return tas;
    }
}
