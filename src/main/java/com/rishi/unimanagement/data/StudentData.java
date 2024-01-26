/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rishi.unimanagement.data;

public class StudentData extends UserData {
    private final double gpa;

    public StudentData(String name, String password, double gpa) {
        super(name, password);
        this.gpa = gpa;
    }
    
    public double getGPA() { 
        return gpa; //calculate.
    }
    
    public Grade getGrades() {
        
    }
    
    public void setGrades() {
        
    }
}