/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.rishi.unimanagement;

import com.rishi.unimanagement.visual.MainFrame;
import com.rishi.unimanagement.data.DataReader;
import javax.swing.SwingUtilities;

public class UniManagement {

    public static void main(String[] args) {
       new DataReader();
       SwingUtilities.invokeLater(() -> {
           new MainFrame().setVisible(true);
       });
    }
}
