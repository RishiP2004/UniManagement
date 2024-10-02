package com.rishi.unimanagement.data;

import java.util.Map;
import org.bson.Document;

public class StudentData implements User {
    private final String name;
    private String password;
    private int section;
    private Map<String, Integer> grades;

    public StudentData(String name, String password, int section, Map<String, Integer> grades) {
        this.name = name;
        this.password = password;
        this.section = section;
        this.grades = grades;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, Integer> grades) {
        this.grades = grades;
    }

    public void setGrade(String subject, int newGrade) {
        if (grades.containsKey(subject)) {
            grades.put(subject, newGrade);
        }
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

    public int getGrade(String subject) {
        return grades.get(subject);
    }

    public double getCGPA() {
        int total = grades.values().stream().mapToInt(Integer::intValue).sum();
        return total / (double) grades.size();
    }

    @Override
    public String getType() {
        return STUDENT;
    }
    
    @Override
    public Document toDocument() {
        return new Document("name", name)
                .append("password", password)
                .append("section", section)
                .append("grades", grades);
    }
}
