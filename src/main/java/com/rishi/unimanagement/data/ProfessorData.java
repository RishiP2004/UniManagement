package com.rishi.unimanagement.data;

import org.bson.Document;

public class ProfessorData extends UserData {
    public ProfessorData(String name, String password) {
        super(name, password);
    }

    @Override
    public String toString() {
        return "Professor: " + name + ", Password: " + password;
    }

    @Override
    public int getType() {
        return PROF;
    }
    
    @Override
    public Document toDocument() {
        return new Document("name", getName())
                .append("password", getPassword());
    }
}