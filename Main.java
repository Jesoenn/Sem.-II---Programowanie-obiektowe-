package org.project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args) {
        //Stworzenie okna
        JFrame window= new JFrame();
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Derby samochodowe");

        EkranGlowny main_menu=new EkranGlowny();
        window.add(main_menu);
        window.pack();
        window.setVisible(true);
        main_menu.start();
        window.remove(main_menu);
        Derby derby =new Derby(main_menu.getNitra(),main_menu.getWaga(),main_menu.getSamochody());
        window.add(derby);
        window.pack();
        derby.setStartingPositions();
        derby.start();
        Zapis zapis=new Zapis(derby);
        zapis.startSaving();

        ZapisWynikow chart = new ZapisWynikow();
        chart.setDataset1(chart.readFile("src/main/java/org/project/dane/explosiveCar.txt"));
        chart.createChart(chart.getDataset1());
        chart.setDataset2(chart.rankingReader("src/main/java/org/project/dane/rankings.txt"));
        chart.createRankChart(chart.getDataset2());
        /*ArrayList<Integer> samochody;
        Derby derby;
        Zapis zapis;
        for(int l=0; l<100; l++){
            System.out.println("POWTORZENIE SYMULACJI: "+(l+1));
            samochody=new ArrayList<>();
            samochody.add(3);
            samochody.add(3);
            samochody.add(3);
            samochody.add(3);
            derby=new Derby(10,5,1000,samochody);
            window.add(derby);
            window.pack();
            window.setVisible(true);
            derby.setStartingPositions();
            derby.start();
            zapis=new Zapis(derby);
            zapis.startSaving();

            derby.normalCars.get(0).carCount=0;
            derby.nitros.get(0).countNitro=0;
            derby.walls.get(0).countSciany=0;
            derby.normalCars.get(0).carsAlive=0;
            window.remove(derby);
        }*/
    }
}
