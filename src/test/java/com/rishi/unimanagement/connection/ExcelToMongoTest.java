package com.rishi.unimanagement.connection;

import com.mongodb.client.*;
import com.rishi.unimanagement.data.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExcelToMongoTest {

    private static MongoDBContainer mongoDBContainer;
    private MongoDatabase database;

    @BeforeEach
    void setUp() {
        mongoDBContainer = new MongoDBContainer("mongo:latest");
        mongoDBContainer.start();
        DatabaseConnectionManager.initialize(mongoDBContainer.getReplicaSetUrl());
        database = DatabaseConnectionManager.getConnection();
    }

    @AfterEach
    void tearDown() {
        MongoIterable<String> collectionNames = database.listCollectionNames();
        for (String collectionName : collectionNames) {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            collection.drop();
            ExcelToMongoDB.markDataAsInserted(collectionName, false);
        }
        ExcelToMongoDB.clearExcelFile(ExcelToMongoDB.TA_PATH);
        ExcelToMongoDB.clearExcelFile(ExcelToMongoDB.STUDENT_PATH);
        ExcelToMongoDB.clearExcelFile(ExcelToMongoDB.PROFESSOR_PATH);
        DatabaseConnectionManager.close();
        mongoDBContainer.stop();
    }

    @Test
    public void testTAExcelToMongoDBInsert() throws IOException {
        String taExcelFilePath = ExcelToMongoDB.TA_PATH;
        createTestExcelFile(taExcelFilePath, User.TA);

        ExcelToMongoDB.readExcelAndInsertToMongo(User.TA, taExcelFilePath, "ta");

        assertTrue(ExcelToMongoDB.isDataAlreadyInserted(User.TA));

        MongoCollection<Document> collection = database.getCollection("ta");
        List<Document> documents = collection.find().into(new java.util.ArrayList<>());

        assertEquals(3, documents.size());

        Document firstUser = documents.get(0);
        assertEquals(User.TA + "_1", firstUser.getString("name"));
        assertEquals("password123", firstUser.getString("password"));
        assertEquals(11, firstUser.getInteger("section"));
    }

    @Test
    public void testStudentExcelToMongoDBInsert() throws IOException {
        String studentExcelFilePath = ExcelToMongoDB.STUDENT_PATH;
        createTestExcelFile(studentExcelFilePath, User.STUDENT);

        ExcelToMongoDB.readExcelAndInsertToMongo(User.STUDENT, studentExcelFilePath, "students");

        assertTrue(ExcelToMongoDB.isDataAlreadyInserted(User.STUDENT));

        MongoCollection<Document> collection = database.getCollection("students");
        List<Document> documents = collection.find().into(new java.util.ArrayList<>());

        assertEquals(3, documents.size());

        Document firstUser = documents.get(0);
        assertEquals(User.STUDENT + "_1", firstUser.getString("name"));
        assertEquals("password123", firstUser.getString("password"));
        assertEquals(102, firstUser.getInteger("section"));
        assertTrue(firstUser.get("grades") instanceof Document);
    }

    @Test
    public void testProfessorExcelToMongoDBInsert() throws IOException {
        String professorExcelFilePath = ExcelToMongoDB.PROFESSOR_PATH;
        createTestExcelFile(professorExcelFilePath, User.PROFESSOR);

        ExcelToMongoDB.readExcelAndInsertToMongo(User.PROFESSOR, professorExcelFilePath, "professors");

        assertTrue(ExcelToMongoDB.isDataAlreadyInserted(User.PROFESSOR));

        MongoCollection<Document> collection = database.getCollection("professors");
        List<Document> documents = collection.find().into(new java.util.ArrayList<>());

        assertEquals(3, documents.size());

        Document firstUser = documents.get(0);
        assertEquals(User.PROFESSOR + "_1", firstUser.getString("name"));
        assertEquals("password123", firstUser.getString("password"));
    }

    private void createTestExcelFile(String excelFilePath, String userType) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TEST_" + userType);

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Password");

        if(Objects.equals(userType, User.STUDENT)) {
            for (int i = 1; i <= 3; i++) {
                headerRow.createCell(2).setCellValue("Section");
                headerRow.createCell(3).setCellValue("Grades");
                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue(userType + "_" + i);
                row.createCell(1).setCellValue("password123");
                row.createCell(2).setCellValue(101 + i);
                row.createCell(3).setCellValue("{\"Math\": 90, \"Science\": 85}");
            }
        }
        if(Objects.equals(userType, User.PROFESSOR)) {
            for (int i = 1; i <= 3; i++) {
                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue(userType + "_" + i);
                row.createCell(1).setCellValue("password123");
            }
        }
        if(Objects.equals(userType, User.TA)) {
            for (int i = 1; i <= 3; i++) {
                Row row = sheet.createRow(i);
                headerRow.createCell(2).setCellValue("Section");
                row.createCell(0).setCellValue(userType + "_" + i);
                row.createCell(1).setCellValue("password123");
                row.createCell(2).setCellValue(10 + i);
            }
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(fileOutputStream);
        }
        workbook.close();
    }
}
