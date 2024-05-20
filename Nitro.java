package org.example;

import java.util.Random;

public class Nitro {
    int predkosc;
    int id;
    int czas;
    int wspolrzedne[];
    Nitro()
    {
        Random wspol = new Random();
        for(int i = 0; i < 2; i++)
            wspolrzedne[i] = wspol.nextInt();
        // pętla na losowanie wsp. do momentu nie zajęcia współrzędnych już istniejących obiektów

    }

    public void destruction()
    {

    }
}
