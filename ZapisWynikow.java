package org.project;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class ZapisWynikow extends JPanel {
    private Scanner reader;
    // ponizej wykresy na rozne rzeczy (do wymyslenia wszystkich mozliwosci)
    private JFreeChart timeDependencies; // wpływ parametrów wejściowych na czas trwania symulacji
    private JFreeChart others;
    private JFreeChart others2;
    private DefaultCategoryDataset dataset1;
    private DefaultCategoryDataset dataset2;
    private String fileName;
    enum carType
    {
        COMMON,
        EXPLOSIVE,
        LASER,
        TIREPOOPER
    }

    public ZapisWynikow()
    {
        dataset1 = new DefaultCategoryDataset();
        //timeDependencies = ChartFactory.createLineChart("time Dependencies: ", "sth", "time", dataset1, PlotOrientation.VERTICAL, true, true, false );

        // dastaset2 do wykresu zal. rankingu auta od czasu trwania symulacji
        dataset2 = new DefaultCategoryDataset();
        others = ChartFactory.createLineChart("Stats: ", "time", "rank", dataset1, PlotOrientation.VERTICAL, true, true, false );
        others2 = ChartFactory.createLineChart("Stats: ", "time", "rank", dataset1, PlotOrientation.VERTICAL, true, true, false );

        ChartPanel chartPanel2 = new ChartPanel(others);
        chartPanel2.setPreferredSize(new Dimension(800, 500));
    }
//    public void updateDataset(int nitro, int time)
//    {
//        nitro = Derby.nitros.size();
//        dataset1.addValue(/*czas*/, "Nitra", nitro);
//    }
    public DefaultCategoryDataset readFile(String fileName) // odczyt danych z pliku
    {
        try
        {
            File file = new File(fileName);
            reader = new Scanner(file);
            Integer lenght = 0;
            while(reader.hasNextLine())
            {
                String line = reader.nextLine(); // od razu pominiecie zbednej w analizie opisowego 1-wszego wiersza
                ArrayList<Integer> fileData = new ArrayList<Integer>();
                for(int i = 0; i < line.length(); i++) {
                    if(line.charAt(i) != ';')
                    {
                        fileData.add((int) line.charAt(i));
                    }
                    lenght++;
                }
                while(lenght > 0) {
                    if(lenght%2 == 1)
                        dataset1.addValue(/*fileData.get(lenght)*/3, "Cars", lenght.toString()); // to bylo robione na czas testow czy wgl te wykresy dzialaja
                    else dataset1.addValue(/*fileData.get(lenght)*/10, "Cars", lenght.toString());
//                    System.out.println("OUTPUT: " + dataset1.getValue("Cars", 0));
                    lenght--;
                }
            }
            return dataset1; // dane wstawiane nastepnie do wykresu
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return dataset1;
    }
    public DefaultCategoryDataset rankingReader(String fileName)
    {
        try
        {
            File file = new File(fileName);
            reader = new Scanner(file);
            Integer lenght = 0;
            Integer witchLine = 0;
            while(reader.hasNextLine())
            {
                String line = reader.nextLine(); // od razu pominiecie zbednej w analizie opisowego 1-wszego wiersza
                ArrayList<Integer> fileData = new ArrayList<Integer>();
                for(int i = 0; i < line.length(); i++) {
                    if(line.charAt(i) != ';')
                    {
                        fileData.add((int) line.charAt(i));
                        lenght++;
                    }
                }
                int counter = 0;
                for(carType type: carType.values())
                {
                    dataset2.addValue(fileData.get(counter), type, witchLine.toString());
                    counter++;
                }
                fileData.clear(); // zeby nie potrzebnie zliczac
                witchLine++;
            }
            return dataset2; // dane wstawiane nastepnie do wykresu
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return dataset2;
    }
    public void createChart(DefaultCategoryDataset dejta)
    {
        try // zapisanie wykresu na dysku
        {
            others = ChartFactory.createLineChart("Stats: ", "time", "rank", dejta, PlotOrientation.VERTICAL, true, true, false );
            ChartUtils.saveChartAsJPEG(new File("src/main/java/org/project/charts/chart.jpg"), others, 500,500);
            others2 = ChartFactory.createLineChart("Stats: ", "SimulationNumber", "rank", dejta, PlotOrientation.VERTICAL, true, true, false );
            ChartUtils.saveChartAsJPEG(new File("src/main/java/org/project/charts/rankChart.jpg"), others2, 500,500);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Błąd tworzenia pliku");
        }
    }
    public void createRankChart(DefaultCategoryDataset dejta)
    {
        try // zapisanie wykresu na dysku
        {
            others2 = ChartFactory.createLineChart("Stats: ", "SimulationNumber", "rank", dejta, PlotOrientation.VERTICAL, true, true, false );
            ChartUtils.saveChartAsJPEG(new File("src/main/java/org/project/charts/rankChart.jpg"), others2, 500,500);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Błąd tworanie pliku");
        }
    }

    public DefaultCategoryDataset getDataset1() {
        return dataset1;
    }
    public DefaultCategoryDataset getDataset2() {
        return dataset2;
    }
    public void setDataset1(DefaultCategoryDataset data) {
        dataset1 = data;
    }
    public void setDataset2(DefaultCategoryDataset data) {
        dataset2 = data;
    }
}
