package org.project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Derby extends JPanel {
    private int carsNormalAmount=6; //TYMCZASOWO ILE SAMOCHODOW, BEDZIE TO LICZONE W EKANGLOWNY
    private int carsExplosiveAmount=3; //Tymczasowo ile eksplodujacych
    private int wallsAmount=6; //Tymczasowo ile scian
    private int nitroAmount=10; //Tymczasowo ile nitr - uzytkownik podaje
    private int carsTirePopersAmount,carsLaserAmount,carsWeight; //liczba wspomnianych rzeczy na mapie, jak wyzej
    public static final int samochodSize=50; //wielkosc samochodu w pixelach
    public static final int screenX=650; //rozmiar ekranu
    public static final int screenY=650;
    public static final int squaresX=screenX/samochodSize; //podzial ekranu na kwadraty -> potrzebne w klasa Mapa
    public static final int squaresY=screenY/samochodSize;
    //Tworzenie obiektow
    public Mapa map; //tymczasowo public
    public ArrayList<Samochod> normalCars=new ArrayList<>(); //tymczasowo public
    public ArrayList<Sciana> walls=new ArrayList<>(); //tymczasowo public
    public ArrayList<Nitro> nitros=new ArrayList<>();
    private Image koniecrozgrywki;
    private boolean simulationFinished=false;
    private double simulationTime = 12.0; // czas do konca trwania symulacji w sekundach
    private int minutes=0;
    private int seconds=0;
    //ponizej do testow
    //Samochod samochod1=new Samochod(this,50,50);
    //Samochod samochod2=new Samochod(this,500,500);
    public Derby(int nitros, int walls,int weight,ArrayList<Integer> cars){
        nitroAmount=nitros;
        wallsAmount=walls;
        carsWeight=weight;
        carsNormalAmount=cars.get(0);
        carsExplosiveAmount=cars.get(1);
        carsLaserAmount=cars.get(2);
        carsTirePopersAmount=cars.get(3);
        map=new Mapa(this);
        setPreferredSize(new Dimension(screenX,screenY));
        setDoubleBuffered(true); //rysowanie wszystkiego jednoczesnie na ekranie
        downloadImages();
    }
    //ustawienie pozycji startowych (na razie dla samochodow zwyklych)
    public void setStartingPositions(){
        int[][] startingMap=map.getMap();
        //Przejscie przez kazde pole na mapie i stworzenie odpowiednich obiektow
        for(int i=0; i<squaresY; i++){
            for(int j=0; j<squaresX; j++){
                if(startingMap[i][j]==3){
                    normalCars.add(new Samochod(this,j*samochodSize,i*samochodSize,carsWeight));
                }
                else if(startingMap[i][j]==4){
                    normalCars.add(new SamochodWybuchowy(this,j*samochodSize,i*samochodSize,carsWeight));
                }
                else if(startingMap[i][j]==5){
                    normalCars.add(new SamochodLaserowy(this,j*samochodSize,i*samochodSize,carsWeight));
                }
                else if(startingMap[i][j]==7){
                    normalCars.add(new SamochodOponowy(this,j*samochodSize,i*samochodSize,carsWeight));
                }
                else if(startingMap[i][j]==1){
                    walls.add(new Sciana(this,j*samochodSize,i*samochodSize,"vertical"));
                }
                else if(startingMap[i][j]==2){
                    walls.add(new Sciana(this,j*samochodSize,i*samochodSize,"horizontal"));
                }
                else if(startingMap[i][j]==6){
                    nitros.add(new Nitro(this,j*samochodSize,i*samochodSize));
                }
            }
        }
        for(Samochod car: normalCars){
            car.updateWalls();
            car.updateNitros();
        }
    }
    //rozpoczecie symulacji
    public void start(){
        double frameTime=16.66667; //60FPS - 1/60*1000ms
        double nextFrame=System.currentTimeMillis()+frameTime; //kiedy nastepna klatka
        double timeLeft; //czas pozostaly do nastepnej klatki
        while(simulationTime > 0.0 && !simulationFinished){
            try{
                timeLeft=nextFrame-System.currentTimeMillis();
                if(timeLeft>0) //jezeli jeszcze zostal czas
                    Thread.sleep((long)timeLeft);
                nextFrame=System.currentTimeMillis()+frameTime;
                simulationTime -= frameTime/1000.0;
            }catch(Exception e){
                e.printStackTrace();
            }
            //czas do zakonczenia
            minutes=(int)simulationTime/60;
            seconds=(int)simulationTime%60;
            gameStateUpdate();
            repaint();
        }
        simulationFinished=true;

        //sprawdzenie rankingu
        for(Samochod car: normalCars){
            System.out.print(car.getRank()+",");
        }

        repaint();
    }
    //zaktualiziowanie obecnego stanu rozgrywki
    /*public void gameStateUpdate(){
        Samochod targetCar;
        for(Samochod normalCar: normalCars){
            if(normalCar.getCarId()==normalCar.getTargetId()){
                normalCar.findTarget();
            }
            targetCar=normalCars.get(normalCar.getTargetId());
            normalCar.movement(targetCar.getCurrentX(), targetCar.getCurrentY());
            for(Sciana wall: walls){
                if(normalCar.getHitbox().intersects(wall.getHitbox())){
                    normalCar.setSpeed(0);
                }
            }
        }
    }*/
    public void gameStateUpdate(){
        if(normalCars.get(0).getCarsAlive()<=1)
            simulationFinished=true;
        for(Samochod normalCar: normalCars){
            normalCar.update();
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); //jak to dodaje to ekran sie czysci
        Graphics2D g2d=(Graphics2D) g;
        map.drawMap(g2d);
        for(Nitro nitro: nitros){
            nitro.generateonMap(g2d);
        }
        for(Samochod normalCar: normalCars){
            if(normalCar instanceof SamochodWybuchowy){
                //jezeli samochod wybuchl, to rysuj wybuch przez okreslony czas
                if(((SamochodWybuchowy) normalCar).getExploded() && ((SamochodWybuchowy) normalCar).getExplosionLength()>0){
                    ((SamochodWybuchowy) normalCar).explosion(g2d);
                }
            }
        }
        for(Samochod normalCar: normalCars){
            normalCar.updateOnMap(g2d);
        }
        for(Sciana wall: walls){
            wall.generateOnMap(g2d);
        }
        if(simulationFinished) //Napis KONIEC ROZGRYWKI
            g2d.drawImage(koniecrozgrywki,(screenX-542)/2,(screenY-26)/2,null);
        //Wyswietlenie czasu na ekranie
        g2d.setColor(Color.white);
        g2d.setFont(new Font("default", Font.BOLD, 40));
        if(seconds>=10)
            g2d.drawString(minutes+":"+seconds,screenX/2-38,40);
        else{
            g2d.drawString(minutes+":0"+seconds,screenX/2-38,40);
        }
    }
    public void downloadImages(){
        try{
            koniecrozgrywki=ImageIO.read(getClass().getResource("/extra/koniecrozgrywki.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public int getWallsAmount(){
        return wallsAmount;
    }
    public int getCarsNormalAmount(){
        return carsNormalAmount;
    }
    public int getCarsExplosiveAmount(){
        return carsExplosiveAmount;
    }
    public int getNitroAmount() {
        return nitroAmount;
    }
    public int getCarsLaserAmount(){
        return carsLaserAmount;
    }
    public int getCarsTirePopersAmount(){
        return carsTirePopersAmount;
    }
}
