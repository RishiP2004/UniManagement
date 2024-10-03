package com.rishi.unimanagement.repo;

import com.mongodb.client.MongoCollection;
import com.rishi.unimanagement.connection.DatabaseConnectionManager;
import com.rishi.unimanagement.data.Admin;
import org.bson.Document;

import java.util.List;

public class AdminRepository implements UserRepository<Admin> {
    private final MongoCollection<Document> adminCollection;
    private static AdminRepository instance;

    private AdminRepository() {
        this.adminCollection = DatabaseConnectionManager.getConnection().getCollection("admin");
    }

    public static AdminRepository getInstance() {
        if (instance == null) {
            instance = new AdminRepository();
        }
        return instance;
    }

    @Override
    public void addUser(Admin user) {}

    @Override
    public Admin getUserByName(String name) {
        Document doc = adminCollection.find(new Document("name", name)).first();

        if (doc != null) {
            return new Admin(doc.getString("name"), doc.getString("password"));
        }
        return null;
    }

    @Override
    public void updateUser(Admin user) {}

    @Override
    public void deleteUser(String name) {}

    @Override
    public List getAllUsers() {
        return List.of();
    }
}
