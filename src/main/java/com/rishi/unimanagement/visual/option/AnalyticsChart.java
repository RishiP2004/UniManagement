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
    private final List<Integer> gradesList; // List to hold the grades passed to the chart

    public AnalyticsChart(List<Integer> gradesList) {
        this.gradesList = gradesList; // Store the passed grades list
        initComponents();
    }
    
    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int[] gradeRanges = new int[5];  // For [0-50, 51-60, 61-70, 71-80, 81-100]

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
        DefaultCategoryDataset dataset = createDataset(); // Get dataset from the grades
        JFreeChart barChart = ChartFactory.createBarChart(
                                "Overall Grade Distribution",
                                "Grade Range",
                                "Number of Students",
                                dataset,
                                PlotOrientation.VERTICAL,
                                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(560, 370));
        setLayout(new BorderLayout()); // Use BorderLayout to manage components
        add(chartPanel, BorderLayout.CENTER); // Add chart panel to the center of the JPanel
    }
}
