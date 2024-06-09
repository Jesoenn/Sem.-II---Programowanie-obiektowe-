package org.project;

import java.util.Scanner;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ZapisWynikow {
    private Scanner reader;
    // ponizej wykresy na rozne rzeczy (do wymyslenia wszystkich mozliwosci)
    private JFreeChart timeDependencies; // wpływ parametrów wejściowych na czas trwania symulacji
    private JFreeChart others;
    private DefaultCategoryDataset dataset1; 
    private DefaultCategoryDataset dataset2;

      public ZapisWynikow(JFreeChart wykres)
    {
        dataset1 = new DefaultCategoryDataset();
        chart1 = ChartFactory.createLineChart();
        dataset2 = new DefaultCategoryDataset();
        chart2 = ChartFactory.createLineChart();
        ChartPanel chartPanel1 = new ChartPanel(timeDependencies);
        chartPanel1.setPreferredSize(new Dimension(400, 400));
        ChartPanel chartPanel2 = new ChartPanel(others);
        chartPanel2.setPreferredSize(new Dimension(400, 400));
    }
  public void savingDataToFile(String fileName)
  {
        try(BufferedWriter save = new BufferedWriter(new FileWriter(fileName, true)))
        {
            save.write("Dane: Samochod, SamochodWybuchowy, SamochodOponowy, SamochodLaserowy, Nitro, Sciana");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
