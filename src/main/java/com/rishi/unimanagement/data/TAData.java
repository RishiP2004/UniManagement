package com.rishi.unimanagement.data;

import org.bson.Document;

public class TAData implements User {
    private final String name;
    private String password;
    private final int section;

    public TAData(String name, String password, int section) {
        this.name = name;
        this.password = password;
        this.section = section;
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
    
    @Override
    public String getType() {
        return TA;
    }

    @Override
    public Document toDocument() {
        return new Document("name", name)
                .append("password", password)
                .append("section", section);
    }
}
