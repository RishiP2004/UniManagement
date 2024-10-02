package com.rishi.unimanagement.visual.option;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AnalyticsChart extends JPanel {
    private final List<Integer> gradesList; 

    public AnalyticsChart(List<Integer> gradesList) {
        this.gradesList = gradesList; 
        initComponents();
    }
    
    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int[] gradeRanges = new int[5];  

        for (Integer grade : gradesList) {
            if (grade <= 50) gradeRanges[0]++;
            else if (grade <= 60) gradeRanges[1]++;
            else if (grade <= 70) gradeRanges[2]++;
            else if (grade <= 80) gradeRanges[3]++;
            else gradeRanges[4]++;
        }

        dataset.addValue(gradeRanges[0], "Grades", "0-50");
        dataset.addValue(gradeRanges[1], "Grades", "51-60");
        dataset.addValue(gradeRanges[2], "Grades", "61-70");
        dataset.addValue(gradeRanges[3], "Grades", "71-80");
        dataset.addValue(gradeRanges[4], "Grades", "81-100");

        return dataset;
    }
    
    private void initComponents() {
        DefaultCategoryDataset dataset = createDataset(); 
        JFreeChart barChart = ChartFactory.createBarChart(
                                "Overall Grade Distribution",
                                "Grade Range",
                                "Number of Students",
                                dataset,
                                PlotOrientation.VERTICAL,
                                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(560, 370));
        setLayout(new BorderLayout()); 
        add(chartPanel, BorderLayout.CENTER);
    }
}
