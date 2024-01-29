package com.rishi.unimanagement;

import com.rishi.unimanagement.visual.MainFrame;
import com.rishi.unimanagement.data.Database;
import javax.swing.SwingUtilities;
//todo: back buttons
public class UniManagement {

    public static void main(String[] args) {
       new Database();
       SwingUtilities.invokeLater(() -> {
           new MainFrame().setVisible(true);
       });
    }
}
