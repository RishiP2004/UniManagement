package com.rishi.unimanagement;

import com.rishi.unimanagement.connection.DatabaseConnectionManager;
import com.rishi.unimanagement.visual.MainFrame;

import javax.swing.SwingUtilities;
//todo: back buttons, encrypt pass
public class UniManagement {

    public static void main(String[] args) {
        DatabaseConnectionManager.initialize(null);

       SwingUtilities.invokeLater(() -> {
           new MainFrame().setVisible(true);
       });
    }
}
