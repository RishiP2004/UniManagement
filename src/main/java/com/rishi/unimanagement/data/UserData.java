/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rishi.unimanagement.data;

public abstract class UserData {
    protected String name;
    protected String password;

    public UserData(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public String getName() { 
        return name;
    }
    
    public String getPassword() { 
        return password;
    }
}
