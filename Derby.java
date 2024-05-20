package org.example;

import  java.awt.*;
import java.util.Scanner;

public class Derby {
    static int time = 45;
    public void start(int ile_aut, int AmountOfBombers, int AountOfSpikers, int ile_nitr) {
        Frame input = new Frame();      // utworzenie okna do wpisywania parametrów wejściowych
        TextField t1 = new TextField(); // textbox do wpisywania parametrów
        t1.setBounds(200, 20,200, 40);
        input.add(t1);
        input.setSize(400,400);
        input.setLayout(null);
        input.setVisible(true);
    }
    public void zakonczenie()
    {

    }
    public void zapis_wynikow()
    {
        Scanner odczyt = new Scanner(System.in);
    }
    public static void main(String[] args)
    {
        while(time > 0)     // petla glowna programu
        {
            /*
            code ...

            */
            time--;
            if(time == 0)
            {

            }
        }
    }

}