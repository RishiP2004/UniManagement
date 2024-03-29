package com.rishi.unimanagement.data;

import java.util.Map;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

public class StudentData extends UserData {
    private int section;
    private final Map<String, Integer> grades;
    public StudentData(String name, String password, int section, Map<String, Integer> grades) {
        super(name, password);
        this.section = section;
        this.grades = grades;
    }
    
    @Override
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
            return false;
        }
    }
    
    private void updateSectionInDatabase(int newSection) {
        try {
            if (Database.get() != null) {
                MongoCollection<Document> collection = Database.get().getCollection(Database.STUDENT_COLLECTION_NAME);
                Document filter = new Document("name", getName());
                Document update = new Document("$set", new Document("section", newSection));
                collection.updateOne(filter, update);
            }
        } catch (Exception e) {
        }
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }

    public String getFormattedGrades() {
        StringBuilder formattedGrades = new StringBuilder();

        for (Map.Entry<String, Integer> entry : grades.entrySet()) {
            String subject = entry.getKey();
            int grade = entry.getValue();

            formattedGrades.append(subject).append(": ").append(grade).append("\n");
        }

        return formattedGrades.toString();
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
            return false;
        }
    }
    
    public int getGrade(String subject) {
        return grades.get(subject);
    }

    private void updateGradeInDatabase(String subject, int newGrade) {
        try {
            if (Database.get() != null) {
                MongoCollection<Document> collection = Database.get().getCollection(Database.STUDENT_COLLECTION_NAME);
                Document filter = new Document("name", getName());
                Document update = new Document("$set", new Document("grades." + subject, newGrade));

                collection.updateOne(filter, update);
            }
        } catch (Exception e) {
        }
    }
    
    public double getCGPA() {
        int total = 0;

        for (int grade : grades.values()) {
            total += grade;
        }
        return total / (double) grades.size();
    }
}