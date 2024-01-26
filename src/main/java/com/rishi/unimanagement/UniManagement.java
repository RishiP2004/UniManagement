/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.rishi.unimanagement;

import com.rishi.unimanagement.visual.MainFrame;
import com.rishi.unimanagement.data.DataReader;
import javax.swing.SwingUtilities;
/*
    System:
    
    Student/Prof/TA login based on default file (Prof cannot edit password)
    Student, TA can change password
    Prof can change passwords
    TA and Prof can manage passwords and grades
*/
public class UniManagement {

    public static void main(String[] args) {
       new DataReader();
       SwingUtilities.invokeLater(() -> {
           new MainFrame().setVisible(true);
       });
    }
}
