package com.rishi.unimanagement.connection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;
import java.util.concurrent.TimeUnit;

public class DatabaseConnectionManager {
    private static MongoDatabase database;

    public static MongoDatabase getConnection() {
        if (database == null) {
            MongoClient mongoClient = MongoClients.create(MongoClientSettings.builder()
                    .applyToConnectionPoolSettings(builder ->
                            builder.maxConnectionIdleTime(60, TimeUnit.SECONDS))
                    .build());
            database = mongoClient.getDatabase("UniManagement");
        }
        return database;
    }
}
