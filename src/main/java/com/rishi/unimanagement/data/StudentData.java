/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rishi.unimanagement.data;

public class StudentData extends UserData {
    private final double gpa;
    private final String tuition;

    public StudentData(String name, String password, double gpa, String tuition) {
        super(name, password);
        this.gpa = gpa;
        this.tuition = tuition;
    }
    
    public double getGPA() { 
        return gpa;
    }
    
    public String getTuition() { 
        return tuition;
    }
    
    @Override
    public String toString() {
        return "Student: " + name + ", Password: " + password + ", GPA: " + gpa + ", Tuition: " + tuition;
    }
}