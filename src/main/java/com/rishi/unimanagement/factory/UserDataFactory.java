package com.rishi.unimanagement.factory;

import com.rishi.unimanagement.data.*;
import org.bson.Document;

import java.util.Map;

public class UserDataFactory {
    public static User createUser(Document document, String type) {
        switch (type) {
            case User.STUDENT:
                return new StudentData(
                        document.getString("name"),
                        document.getString("password"),
                        document.getInteger("section"),
                        document.get("grades", Map.class)
                );
            case User.TA:
                return new TAData(
                        document.getString("name"),
                        document.getString("password"),
                        document.getInteger("section")
                );
            case User.PROFESSOR:
                return new ProfessorData(
                        document.getString("name"),
                        document.getString("password")
                );
            default:
                throw new IllegalArgumentException("Invalid user type");
        }
    }

    public static Document buildDocument(String type, String name, String password, Integer section, Map<String, Object> grades) {
        Document document = new Document("name", name)
                .append("password", password);

        switch (type) {
            case User.STUDENT:
                if (section != null) {
                    document.append("section", section);
                }
                if (grades != null) {
                    document.append("grades", grades);
                }
                break;

            case User.TA:
                if (section != null) {
                    document.append("section", section);
                }
                break;

            case User.PROFESSOR:
                break;
            //Not building admin, manually insert ONLY.
            default:
                throw new IllegalArgumentException("Invalid user type");
        }

        return document;
    }

}
