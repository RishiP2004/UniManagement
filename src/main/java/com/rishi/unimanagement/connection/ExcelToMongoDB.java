package com.rishi.unimanagement.connection;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rishi.unimanagement.factory.UserDataFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class ExcelToMongoDB {

    private static final String FLAG_FILE_PATH = "insert_status.txt";

    public static final String TA_PATH = "ta_excel.excel";
    public static final String STUDENT_PATH = "student_excel.excel";
    public static final String PROFESSOR_PATH = "professor_excel.excel";

    public static void readExcelAndInsertToMongo(String type, String excelFilePath, String collectionName) throws IOException {
        if (isDataAlreadyInserted(type)) {
            return;
        }
        MongoDatabase database = DatabaseConnectionManager.getConnection();
        MongoCollection<Document> collection = database.getCollection(collectionName);

        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            String name = row.getCell(0).getStringCellValue();
            String password = row.getCell(1).getStringCellValue();
            Integer section = null;
            Map<String, Object> grades = null;

            if (row.getCell(2) != null && row.getCell(2).getCellType() == CellType.NUMERIC) {
                section = (int) row.getCell(2).getNumericCellValue();
            }

            if (row.getCell(3) != null && row.getCell(3).getCellType() == CellType.STRING) {
                String gradesString = row.getCell(3).getStringCellValue();

                grades = parseGrades(gradesString);
            }
            collection.insertOne(UserDataFactory.buildDocument(type, name, password, section, grades));
        }
        workbook.close();
        fileInputStream.close();

        markDataAsInserted(type);
        System.out.println("Data has been successfully inserted into MongoDB.");
    }
    
    public static Map<String, Object> parseGrades(String gradesString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convert JSON string to Map
            return objectMapper.readValue(gradesString, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle error appropriately
        }
    }

    private static boolean isDataAlreadyInserted(String type) {
        try {
            File flagFile = new File(FLAG_FILE_PATH);

            if (flagFile.exists()) {
                Scanner scanner = new Scanner(flagFile);
                String status = scanner.nextLine();
                scanner.close();
                return status.equalsIgnoreCase(type + "_inserted: true");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void markDataAsInserted(String type) {
        try (FileWriter writer = new FileWriter(FLAG_FILE_PATH)) {
            writer.write(type + "_inserted: true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
