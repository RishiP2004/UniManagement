package com.rishi.unimanagement;

import com.rishi.unimanagement.visual.MainFrame;

import javax.swing.SwingUtilities;
//todo: back buttons
public class UniManagement {

    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
           new MainFrame().setVisible(true);
       });
    }
}
