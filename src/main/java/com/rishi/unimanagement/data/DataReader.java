/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rishi.unimanagement.data;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class DataReader {
    public static ArrayList<StudentData> studentData;
    public static ArrayList<ProfessorData> profData;
    public static ArrayList<TAData> taData;
    
    public DataReader() {
        read();
        ArrayList<StudentData> studentData = new ArrayList<>();
        ArrayList<ProfessorData> profData = new ArrayList<>();
        ArrayList<TAData> taData = new ArrayList<>();
    }

    private void read() {
        try {
            File file = new File("data.txt");
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] fields = line.split(" ");
                    String user = fields[1];
                    String password = fields[2];
                    
                    switch (fields[0]) {
                        case "student" ->                         {
                            double gpa = Double.parseDouble(fields[3]);
                            String tuition = fields[4];
                            StudentData data = new StudentData(user, password, gpa, tuition);
                            studentData.add(data);
                        }
                        case "professor" ->                         {
                            ProfessorData data = new ProfessorData(user, password);
                            profData.add(data);
                        }
                        case "ta" ->                         {
                            TAData data = new TAData(user, password);
                            taData.add(data);
                        }
                        default -> {
                        }
                    }
                }
            }
        }  catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
    
    public static void editData() {
        
    }
    
    public static StudentData getStudentData(String name) {
        for(StudentData data : studentData) {
            if(data.getName().equals(name)) return data;
        }
        return null;
    }
    
    public static TAData getTAData(String name) {
        for(TAData data : taData) {
            if(data.getName().equals(name)) return data;
        }
        return null;
    }
    
    public static ProfessorData getProfessorData(String name) {
        for(ProfessorData data : profData) {
            if(data.getName().equals(name)) return data;
        }
        return null;
    }
    
    public static int getType(String name) {
        StudentData studentData = DataReader.getStudentData(name);
        if (studentData != null) {
            return 0;
        }

        TAData taData = DataReader.getTAData(name);
        if (taData != null) {
            return 1;
        }

        ProfessorData professorData = DataReader.getProfessorData(name);
        if (professorData != null) {
            return 2;
        }

        return -1;
    }
    
    public static String getPasswordForUserType(String name) {
        int userType = getType(name);

        return switch (userType) {
            case 0 -> DataReader.getStudentData(name).getPassword();
            case 1 -> DataReader.getTAData(name).getPassword();
            case 2 -> DataReader.getProfessorData(name).getPassword();
            default -> null;
        };
    }
}
