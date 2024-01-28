/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rishi.unimanagement.data;

import com.mongodb.client.Document;

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
            e.printStackTrace();
            return false;
        }
    }
    
    private void updateSectionInDatabase(int newSection) {
        try {
            if (Database.get() != null) {
                MongoCollection<Document> collection = Database.database.getCollection(Database.TA_COLLECTION_NAME);
                Document filter = new Document("name", getName());
                Document update = new Document("$set", new Document("section", newSection));
                collection.updateOne(filter, update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "TA: " + name + ", Password: " + password;
    }
}