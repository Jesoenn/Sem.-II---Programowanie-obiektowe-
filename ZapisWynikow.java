//package org.project;
//
//import java.util.ArrayList;
//import java.util.Scanner;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.DefaultCategoryDataset;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class ZapisWynikow {
//    private Scanner reader;
//    // ponizej wykresy na rozne rzeczy (do wymyslenia wszystkich mozliwosci)
//    private JFreeChart timeDependencies; // wpływ parametrów wejściowych na czas trwania symulacji
//    private JFreeChart others;
//    private DefaultCategoryDataset timeDepDataSet;
//    private DefaultCategoryDataset dataset2;
//
//      public ZapisWynikow(JFreeChart wykres)
//    {
//        timeDepDataSet = new DefaultCategoryDataset();
//        timeDependencies = ChartFactory.createLineChart("time Dependencies: ", "sth", "time", dataset1, PlotOrientation.VERTICAL, true, true, false );
//        dataset2 = new DefaultCategoryDataset();
//        others = ChartFactory.createLineChart();
//        ChartPanel chartPanel1 = new ChartPanel(timeDependencies);
//        chartPanel1.setPreferredSize(new Dimension(400, 400));
//        ChartPanel chartPanel2 = new ChartPanel(others);
//        chartPanel2.setPreferredSize(new Dimension(400, 400));
//    }
//    public DefaultCategoryDataset readFile(File file)
//    {
//        try
//        {
//            file = new File(fileName);
//            reader = new Scanner(file);
//            Integer lenght = 0;
//            while(reader.hasNextLine())
//            {
//                String line = reader.nextLine();
//                ArrayList<Integer> fileData = new ArrayList<Integer>();
//                for(int i = 0; i < line.length(); i++) {
//                    if(line.charAt(i) != ';')
//                    {
//                        fileData.add((int)line.charAt(i));
//                    }
//                    lenght++;
//                }
//                while(lenght > 0) {
//                    dataset1.addValue(fileData.get(lenght), "Cars", lenght.toString());
//                    lenght--;
//                }
//            }
//            return dataset1;
//        }
//        catch(FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//        return dataset1;
//    }
//}
