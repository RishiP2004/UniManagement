package com.rishi.unimanagement.data;

import org.bson.Document;
import com.mongodb.client.MongoCollection;

public class TAData extends UserData{
    private int section;
    
    public TAData(String name, String password, int section) {
        super(name, password);
    }
    
    public int getSection() {
        return section;
    }
    
    public boolean setSection(int newSection) {
        try {
            section = newSection;
            updateSectionInDatabase(newSection);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void updateSectionInDatabase(int newSection) {
        try {
            if (Database.get() != null) {
                MongoCollection<Document> collection = Database.get().getCollection(Database.TA_COLLECTION_NAME);
                Document filter = new Document("name", getName());
                Document update = new Document("$set", new Document("section", newSection));
                collection.updateOne(filter, update);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getType() {
        return TA;
    }
}