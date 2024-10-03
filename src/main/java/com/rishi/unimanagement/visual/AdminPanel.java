package com.rishi.unimanagement.visual;

import com.rishi.unimanagement.connection.ExcelToMongoDB;
import com.rishi.unimanagement.data.Admin;
import java.awt.CardLayout;
import java.io.IOException;
import javax.swing.JPanel;

import com.rishi.unimanagement.data.User;
import com.rishi.unimanagement.repo.AdminRepository;

public class AdminPanel extends javax.swing.JPanel {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final Admin admin;
    /**
     * Creates new form AdminPanel
     * @param cardLayout
     * @param cardPanel
     */
    public AdminPanel(CardLayout cardLayout, JPanel cardPanel, Admin admin) {
        this.admin = admin;
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

        taButton = new javax.swing.JButton();
        studentButton = new javax.swing.JButton();
        profButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        taButton.setText("Import TAs");
        taButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    taButtonActionPerformed(evt);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        studentButton.setText("Import Students");
        studentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    studentButtonActionPerformed(evt);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        profButton.setText("Import Professors");
        profButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    profButtonActionPerformed(evt);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        jLabel2.setText("Logged in as ADMIN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(taButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(studentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(profButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(128, 128, 128))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(152, 152, 152))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(studentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(taButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(profButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void taButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//GEN-FIRST:event_taButtonActionPerformed
        ExcelToMongoDB.readExcelAndInsertToMongo(User.TA, ExcelToMongoDB.TA_PATH, "ta");
    }//GEN-LAST:event_taButtonActionPerformed

    private void studentButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//GEN-FIRST:event_studentButtonActionPerformed
        ExcelToMongoDB.readExcelAndInsertToMongo(User.TA, ExcelToMongoDB.STUDENT_PATH, "students");
    }//GEN-LAST:event_studentButtonActionPerformed

    private void profButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//GEN-FIRST:event_profButtonActionPerformed
        ExcelToMongoDB.readExcelAndInsertToMongo(User.TA, ExcelToMongoDB.PROFESSOR_PATH, "professors");
    }//GEN-LAST:event_profButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton profButton;
    private javax.swing.JButton studentButton;
    private javax.swing.JButton taButton;
    // End of variables declaration//GEN-END:variables
}