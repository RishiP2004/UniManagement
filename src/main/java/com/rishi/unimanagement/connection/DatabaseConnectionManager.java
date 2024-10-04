package com.rishi.unimanagement.connection;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;
import java.util.concurrent.TimeUnit;

public class DatabaseConnectionManager {
    private static MongoDatabase database;
    private static MongoClient mongoClient;

    public static void initialize(String mongoUri) {
        String uri = (mongoUri == null || mongoUri.isEmpty()) ? "mongodb://localhost:27017" : mongoUri;

        ConnectionString connectionString = new ConnectionString(uri);

        MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyConnectionString(connectionString)
                        .applyToConnectionPoolSettings(builder ->
                                builder.maxConnectionIdleTime(60, TimeUnit.SECONDS))
                        .build()
        );

        database = mongoClient.getDatabase("UniManagement");
    }

    // Method to retrieve the database instance
    public static MongoDatabase getConnection() {
        if (database == null) {
            throw new IllegalStateException("MongoDB connection is not initialized. Call initializeMongoDb() first.");
        }
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
        database = null; // Reset the database reference
        mongoClient = null; // Reset the client reference
    }
}
