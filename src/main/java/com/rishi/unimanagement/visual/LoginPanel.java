/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.rishi.unimanagement.visual;

import javax.swing.*;
import java.awt.*;
import com.rishi.unimanagement.data.Database;
import static com.rishi.unimanagement.data.Database.getUserData;
import com.rishi.unimanagement.data.UserData;

public class LoginPanel extends javax.swing.JPanel {
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    
    /**
     * Creates new form LoginPanel
     */
    public LoginPanel(CardLayout cardLayout, JPanel cardPanel) {
        initComponents();
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        enterUsernameLabel = new javax.swing.JLabel();
        enterPasswordLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();

        enterUsernameLabel.setText("Enter Username:");

        enterPasswordLabel.setText("Enter Password:");

        loginButton.setText("LOGIN");
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginButtonMouseClicked(evt);
            }
        });

        passwordField.setText("jPasswordField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(enterPasswordLabel)
                    .addComponent(enterUsernameLabel))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loginButton)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enterUsernameLabel)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enterPasswordLabel)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(loginButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseClicked
        if (performLogin()) {
                String userName = usernameTextField.getText();
                
                switch(Database.getUserData(userName).getType()) {
                    case 0 -> {
                        StudentPanel studentPanel = new StudentPanel(cardLayout, cardPanel, userName);

                        cardPanel.add(studentPanel, "student");
                        cardLayout.show(cardPanel, "student");
                    }
                    case 1 -> {
                        TAPanel taPanel = new TAPanel(cardLayout, cardPanel, userName);

                        cardPanel.add(taPanel, "ta");
                        cardLayout.show(cardPanel, "ta");
                    }   
                    case 2 -> {
                        ProfessorPanel profPanel = new ProfessorPanel(cardLayout, cardPanel, userName);

                        cardPanel.add(profPanel, "professor");
                        cardLayout.show(cardPanel, "professor");
                    }
                }
            }
    }//GEN-LAST:event_loginButtonMouseClicked

    private boolean performLogin() {
        String name = usernameTextField.getText();

        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);
        
        UserData userData = getUserData(name);
        return (userData != null) && userData.getPassword().length() > 0 && userData.getPassword().equals(password);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel enterPasswordLabel;
    private javax.swing.JLabel enterUsernameLabel;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
