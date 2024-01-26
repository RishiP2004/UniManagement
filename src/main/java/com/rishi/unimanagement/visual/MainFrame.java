/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rishi.unimanagement.visual;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    
    public MainFrame() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        LoginPanel loginPanel = new LoginPanel(cardLayout, cardPanel);
        cardPanel.add(loginPanel, "login");

        add(cardPanel);

        cardLayout.show(cardPanel, "login");   
    }
}
