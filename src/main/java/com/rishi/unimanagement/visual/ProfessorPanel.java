package com.rishi.unimanagement.visual;

import com.rishi.unimanagement.data.ProfessorData;
import java.awt.CardLayout;
import javax.swing.JPanel;
import com.rishi.unimanagement.repo.StudentRepository;
import com.rishi.unimanagement.visual.option.StudentManagementOption;
import com.rishi.unimanagement.visual.option.TAManagementOption;
import com.rishi.unimanagement.visual.option.AnalyticsChart;

public class ProfessorPanel extends javax.swing.JPanel {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final ProfessorData prof;
    /**
     * Creates new form ProfessorPanel
     * @param cardLayout
     * @param cardPanel
     */
    public ProfessorPanel(CardLayout cardLayout, JPanel cardPanel, ProfessorData prof) {
        this.prof = prof;
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

        jLabel1 = new javax.swing.JLabel();
        taButton = new javax.swing.JButton();
        studentButton = new javax.swing.JButton();
        analyticsButton = new javax.swing.JButton();

        String name = prof.getName();
        jLabel1.setText(String.format("Logged in as %d", name));

        taButton.setText("TA Management");
        taButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taButtonActionPerformed(evt);
            }
        });

        studentButton.setText("Student Management");
        studentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentButtonActionPerformed(evt);
            }
        });

        analyticsButton.setText("Analytics");
        analyticsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analyticsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(154, 154, 154))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(taButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(studentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(analyticsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(128, 128, 128))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(23, 23, 23)
                .addComponent(studentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(taButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(analyticsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void taButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taButtonActionPerformed
        TAManagementOption panel = new TAManagementOption(cardLayout, cardPanel);

        cardPanel.add(panel, "taManage");
        cardLayout.show(cardPanel, "taManage");
    }//GEN-LAST:event_taButtonActionPerformed

    private void studentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentButtonActionPerformed
        StudentManagementOption panel = new StudentManagementOption(cardLayout, cardPanel);

        cardPanel.add(panel, "studentManage");
        cardLayout.show(cardPanel, "studentManage");
    }//GEN-LAST:event_studentButtonActionPerformed

    private void analyticsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analyticsButtonActionPerformed
        AnalyticsChart panel = new AnalyticsChart(StudentRepository.getInstance().getAllGrades());

        cardPanel.add(panel, "analyticsChart");
        cardLayout.show(cardPanel, "analyticsChart");
    }//GEN-LAST:event_analyticsButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton analyticsButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton studentButton;
    private javax.swing.JButton taButton;
    // End of variables declaration//GEN-END:variables
}
