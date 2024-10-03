package com.rishi.unimanagement.visual;

import com.rishi.unimanagement.service.UserService;
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
