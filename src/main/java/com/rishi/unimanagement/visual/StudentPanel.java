package com.rishi.unimanagement.visual;

import com.rishi.unimanagement.data.StudentData;
import com.rishi.unimanagement.service.StudentService;
import com.rishi.unimanagement.visual.option.StudentCGPAOption;
import com.rishi.unimanagement.visual.option.StudentGradesOption;
import com.rishi.unimanagement.visual.option.ChangePassOption;
import java.awt.CardLayout;
import javax.swing.JPanel;

public class StudentPanel extends JPanel {
    private final StudentData student;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    /**
     * @param cardLayout
     * @param cardPanel
     * @param student 
     */
    public StudentPanel(CardLayout cardLayout, JPanel cardPanel, StudentData student) {
        this.student = student;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cgpaButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        gradesButton = new javax.swing.JButton();
        changePassButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        cgpaButton.setText("Check CGPA");
        cgpaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cgpaButtonActionPerformed(evt);
            }
        });

        String name = student.getName();
        jLabel1.setText(String.format("Logged in as Student: %d", name));

        gradesButton.setText("Check Grades");
        gradesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradesButtonActionPerformed(evt);
            }
        });

        changePassButton.setText("change password");
        changePassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePassButtonActionPerformed(evt);
            }
        });

        int section = student.getSection();
        jLabel2.setText(String.format("Section: %d", section));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(changePassButton)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(gradesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                                .addComponent(cgpaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(122, 122, 122))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(cgpaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(gradesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(changePassButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cgpaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cgpaButtonActionPerformed
        StudentCGPAOption panel = new StudentCGPAOption(student);

        cardPanel.add(panel, "cgpa");
        cardLayout.show(cardPanel, "cgpa");
    }//GEN-LAST:event_cgpaButtonActionPerformed

    private void changePassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePassButtonActionPerformed
        ChangePassOption panel = new ChangePassOption(student, StudentService.getInstance());

        cardPanel.add(panel, "changepass");
        cardLayout.show(cardPanel, "changepass");
    }//GEN-LAST:event_changePassButtonActionPerformed

    private void gradesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradesButtonActionPerformed
        StudentGradesOption panel = new StudentGradesOption(student);

        cardPanel.add(panel, "grades");
        cardLayout.show(cardPanel, "grades");
    }//GEN-LAST:event_gradesButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cgpaButton;
    private javax.swing.JButton changePassButton;
    private javax.swing.JButton gradesButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
