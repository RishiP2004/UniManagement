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
}
