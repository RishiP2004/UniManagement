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
    
    public void setSection(int newSection) {
        try {
            section = newSection;
            updateSectionInDatabase(newSection);
        } catch (Exception ignored) {
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
        } catch (Exception ignored) {
        }
    }

    @Override
    public int getType() {
        return TA;
    }

    @Override
    public Document toDocument() {
        return new Document("name", getName())
                .append("password", getPassword())
                .append("section", getSection());
    }
}