package org.project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Derby extends JPanel {
    private int carsNormalAmount=5; //TYMCZASOWO ILE SAMOCHODOW, BEDZIE TO LICZONE W EKANGLOWNY
    private int wallsAmount=5;
    private int carsExplosiveAmount,carsTirePopersAmount,nitroAmount; //liczba wspomnianych rzeczy na mapie, jak wyzej
    public static final int samochodSize=50; //wielkosc samochodu w pixelach
    public static final int screenX=650; //rozmiar ekranu
    public static final int screenY=650;
    private int counter = 0; // zliczanie lczby iteracji(potrzebne do odbicia sie od sciany)
    public static final int squaresX=screenX/samochodSize; //podzial ekranu na kwadraty -> potrzebne w klasa Mapa
    public static final int squaresY=screenY/samochodSize;
    //Tworzenie obiektow
    private Mapa map=new Mapa(this);
    private ArrayList<Samochod> normalCars=new ArrayList<>();
    private ArrayList<Sciana> walls=new ArrayList<>();
    //ponizej do testow
    //Samochod samochod1=new Samochod(this,50,50);
    //Samochod samochod2=new Samochod(this,500,500);
    public Derby(){
        setPreferredSize(new Dimension(screenX,screenY));
        setDoubleBuffered(true); //rysowanie wszystkiego jednoczesnie na ekranie
    }
    //ustawienie pozycji startowych (na razie dla samochodow zwyklych)
    public void setStartingPositions(){
        int[][] startingMap=map.getMap();
        //Przejscie przez kazde pole na mapie i stworzenie odpowiednich obiektow
        for(int i=0; i<squaresY; i++){
            for(int j=0; j<squaresX; j++){
                if(startingMap[i][j]==3){
                    normalCars.add(new Samochod(this,j*samochodSize,i*samochodSize));
                }
                else if(startingMap[i][j]==1){
                    walls.add(new Sciana(this,j*samochodSize,i*samochodSize,"vertical"));
                }
                else if(startingMap[i][j]==2){
                    walls.add(new Sciana(this,j*samochodSize,i*samochodSize,"horizontal"));
                }
            }
        }
    }
    //rozpoczecie symulacji
    public void start(){
        double frameTime=16.66667; //60FPS - 1/60*1000ms
        double nextFrame=System.currentTimeMillis()+frameTime; //kiedy nastepna klatka
        double timeLeft; //czas pozostaly do nastepnej klatki
        while(true){
            try{
                timeLeft=nextFrame-System.currentTimeMillis();
                if(timeLeft>0) //jezeli jeszcze zostal czas
                    Thread.sleep((long)timeLeft);
                nextFrame=System.currentTimeMillis()+frameTime;
            }catch(Exception e){
                e.printStackTrace();
            }
            gameStateUpdate();
            repaint();
            counter++;
            for(Samochod normalCar : normalCars)
            {
                normalCar.setIterationMoment(counter);
            }
        }
    }
    //zaktualiziowanie obecnego stanu rozgrywki
    public void gameStateUpdate(){
        Samochod targetCar;
        for(Samochod normalCar: normalCars){
            if(normalCar.getCarId()==normalCar.getTargetId()){
                normalCar.findTarget();
            }
            targetCar=normalCars.get(normalCar.getTargetId());
            normalCar.movement(targetCar.getCurrentX(), targetCar.getCurrentY());
            for(Sciana wall: walls){
                if(normalCar.getHitbox().intersects(wall.getHitbox())){
                    // zmiana kierunku o 180 stopni
                    normalCar.setSpeed(-normalCar.getSpeed()); //ustawienie predkosci samochodu na zero - na czas testow
                    normalCar.setIterationMoment(counter);
                    normalCar.setIterationBuf(counter);
                }
            }
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); //jak to dodaje to ekran sie czysci
        Graphics2D g2d=(Graphics2D) g;
        map.drawMap(g2d);
        for(Samochod normalCar: normalCars){
            normalCar.updateOnMap(g2d);
        }
        for(Sciana wall: walls){
            wall.generateOnMap(g2d);
        }
    }
    public int getWallsAmount(){
        return wallsAmount;
    }
    public int getCarsNormalAmount(){
        return carsNormalAmount;
    }
}
