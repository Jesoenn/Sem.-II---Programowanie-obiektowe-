package org.project;

import javax.swing.*;
import java.awt.*;

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

        Derby derby =new Derby();
        window.add(derby);
        window.pack();
        //window.setVisible(true);
        derby.setStartingPositions();
        derby.start();

    }
}