package com.rishi.unimanagement.data;

import org.bson.Document;

public class ProfessorData implements User {
    private final String name;
    private String password;

    public ProfessorData(String name, String password) {
        this.name = name;
        this.password = password;
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

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String getType() {
        return PROFESSOR;
    }

    @Override
    public Document toDocument() {
        return new Document("name", name)
                .append("password", password);
    }
}
