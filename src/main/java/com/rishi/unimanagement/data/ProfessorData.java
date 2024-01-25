/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rishi.unimanagement.data;

public class ProfessorData extends UserData {
    public ProfessorData(String name, String password) {
        super(name, password);
    }

    @Override
    public String toString() {
        return "Professor: " + name + ", Password: " + password;
    }
}