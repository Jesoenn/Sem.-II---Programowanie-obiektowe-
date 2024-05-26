package org.project;

import javax.swing.*;

public class Main{
    public static void main(String[] args) {
        //Stworzenie okna
        JFrame window= new JFrame();
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Derby samochodowe");

        Derby derby =new Derby();
        window.add(derby);
        window.pack();
        window.setVisible(true);
        /* EKRAN STARTOWY Z WYBORAMI, POKI CO NIC NIE ROBI
        EkranGlowny main_menu=new EkranGlowny(window);
        main_menu.start();
        window.getContentPane().removeAll();
        window.repaint(); */
        derby.setStartingPositions();
        derby.start();

    }
}