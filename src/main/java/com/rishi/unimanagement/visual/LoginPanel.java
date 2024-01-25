package com.rishi.unimanagement.visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import com.rishi.unimanagement.data.DataReader;

public class LoginPanel extends JPanel {
    private final JTextField userTextField;
    private final JPasswordField passwordField;

    public LoginPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);

        userTextField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(userTextField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        loginButton.addActionListener((ActionEvent e) -> {
            if (performLogin()) {
                cardLayout.show(cardPanel, "options");
            }
        });
    }
    
    private boolean performLogin() {
        String name = userTextField.getText();

        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        String testPass = DataReader.getPasswordForUserType(name);

        return !name.isEmpty() && password.length() > 0 && testPass.equals(password);
    }
}