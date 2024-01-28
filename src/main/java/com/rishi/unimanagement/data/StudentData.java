/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rishi.unimanagement.data;

import java.util.Map;
import com.mongodb.client.Document;

public class StudentData extends UserData {
    private int section;
    private Map<String, Integer> grades;

    public StudentData(String name, String password, int section, Map<String, Integer> grades) {
        super(name, password);
        this.section = section;
        this.grades = grades;
    }
    
    public int getType() {
        return STUDENT;
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
                MongoCollection<Document> collection = Database.database.getCollection(Database.STUDENT_COLLECTION_NAME);
                Document filter = new Document("name", getName());
                Document update = new Document("$set", new Document("section", newSection));
                collection.updateOne(filter, update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }

    public boolean setGrade(String subject, int newGrade) {
        try {
            if (grades.containsKey(subject)) {
                grades.put(subject, newGrade);
                updateGradeInDatabase(subject, newGrade);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updateGradeInDatabase(String subject, int newGrade) {
        try {
            if (Database.get() != null) {
                MongoCollection<Document> collection = Database.database.getCollection(Database.STUDENT_COLLECTION_NAME);
                Document filter = new Document("name", getName());
                Document update = new Document("$set", new Document("grades." + subject, newGrade));

                collection.updateOne(filter, update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}