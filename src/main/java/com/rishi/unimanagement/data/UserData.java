package com.rishi.unimanagement.data;

import org.bson.Document;
import com.mongodb.client.MongoCollection;

public abstract class UserData {
    public static final int STUDENT = 1;
    public static final int TA = 2;
    public static final int PROF = 3;
    
    protected String name;
    protected String password;

    public UserData(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public abstract int getType();
    
    public String getName() { 
        return name;
    }
    
    public String getPassword() { 
        return password;
    }
    
    public boolean setPassword(String newPass) {
        try {
            password = newPass;
            updatePasswordInDatabase(newPass);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void updatePasswordInDatabase(String newPass) {
        try {
            if (Database.get() != null) {
                String collectionName = "";
                switch(getType()) {
                    case 0 -> {
                        collectionName = Database.STUDENT_COLLECTION_NAME;
                    }
                    case 1 -> {
                        collectionName = Database.TA_COLLECTION_NAME;
                    }
                    case 2 -> {
                        collectionName = Database.PROFESSOR_COLLECTION_NAME;
                    }
                }
                MongoCollection<Document> collection = Database.get().getCollection(collectionName);
                Document filter = new Document("name", getName());
                Document update = new Document("$set", new Document("password", newPass));
                collection.updateOne(filter, update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
